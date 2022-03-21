package controllers;

import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import com.typesafe.config.ConfigFactory;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import models.FreelancerClient;
import models.Projects;
import models.SearchResult;

import org.junit.Test;
import play.Application;
import play.cache.AsyncCacheApi;
import play.inject.Bindings;
import play.libs.ws.WSClient;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertTrue;

public class SearchControllerTest extends WithApplication {

//    @Override
//    protected Application provideApplication() {
//        return new GuiceApplicationBuilder().build();
//    }

//    @Test
//    public void testIndex() {
//        Http.RequestBuilder request = new Http.RequestBuilder()
//                .method(GET)
//                .uri("/");
//
//        Result result = route(app, request);
//        assertEquals(OK, result.status());
//    }

    public static class FakeFreelancerClient extends FreelancerClient{
        @Inject
        public FakeFreelancerClient(WSClient client, AsyncCacheApi cache, Config config) {
            super(client, cache, config);
        }

        
        @Override
        public CompletionStage<SearchResult> searchProjects(String query){
            if(query.equals("python developers") ){
                SearchResult result = new SearchResult();
                HashMap<String,Integer> map = new HashMap<>();
                map.put("Excel",55);
                map.put("Data Mining",334);
                map.put("Web Scraping",95);
                map.put("Python",13);
                result.setProjects(Arrays.asList(new Projects("27127619","Looking for PYTHON developer for an app fix","I have an small application made on Python which worked excellent until data source was updated to n","fixed","Mar 20 2022",map)));
                result.setInput(query);
                return CompletableFuture.completedFuture(result);
            }
            throw new AssertionError("Unkown query");
        }

    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .bindings(Bindings.bind(FreelancerClient.class).to(FakeFreelancerClient.class))
                .build();
    }

    /**
     * testEmptyHomePage: default method.
     */
    @Test
    public void testEmptyHomePage() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(Helpers.GET)
                .uri("/");
        Result result = Helpers.route(app, request);
        assertEquals(Http.Status.OK, result.status());
        // Homepage contains a search box (see index.scala.html)
        assertTrue(Helpers.contentAsString(result)
                .contains("<input type=\"text\" class=\"form-control\" placeholder=\"Enter search terms\" id=\"input\" name=\"input\">"));
        assertTrue(result.session().get(SearchController.SESSION_ID).isPresent());
    }

    /**
     * testSearchWithoutInput: default method.
     */
    @Test
    public void testSearchWithoutInput() {
        Http.RequestBuilder searchRequest = new Http.RequestBuilder()
                .method(Helpers.POST)
                .uri("/search");
        Result result = Helpers.route(app, searchRequest);
        // Without input, we just redirect to the index page
        assertEquals(Http.Status.SEE_OTHER, result.status());
    }

    @Test
    public void testSearch() {
        // 1. Create a POST request containing a search query
        // 2. Execute a search request
        // 3. Verify a search response is redirected to the index page and contain a SESSION_ID
        // 4. Create a GET / request with the session id from the response from the search request
        // 5. Verify that the search history should be returned in the index page
        Http.RequestBuilder searchRequest = new Http.RequestBuilder()
                .method(Helpers.POST)
                .uri("/search")
                // simulate the search input from the search box
                .bodyForm(Collections.singletonMap("input", "python developers"))
                .session(SearchController.SESSION_ID, "session_1");
        Result searchResult = Helpers.route(app, searchRequest);
        // once the search action is finished, we redirect to the index page
        assertEquals(Http.Status.SEE_OTHER, searchResult.status());
        assertEquals(Optional.of("session_1"), searchResult.session().get(SearchController.SESSION_ID));

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(Helpers.GET)
                .uri("/")
                .session(SearchController.SESSION_ID, "session_1");
        Result result = Helpers.route(app, request);
        assertEquals(Http.Status.OK, result.status());

        String html = Helpers.contentAsString(result);
        // Two users
        assertTrue(html.contains("<a href=\"/profile/27127619\">27127619</a>"));
//        assertTrue(html.contains("<a href=\"/profile/55074624\">55074624</a>"));
        // Two Titles
        assertTrue(html.contains("<a href=\"https://www.freelancer.com/projects/python/Looking-for-PYTHON-developer-for-33257466\">Looking for PYTHON developer for an app fix</a>"));
//        assertTrue(html.contains("<a href=\"https://www.freelancer.com/projects/python/build-application-with-kivy-python\">build application with kivy,python and  buildozer </a>"));
        // Two topics
        assertTrue(html.contains("<a href=\"/projectStat?prj=I+have+an+small+application+made+on+Python+which+worked+excellent+until+data+source+was+updated+to+n\">Project Stats</a>"));
//        assertTrue(html.contains("<a href=\"/projectStat?prj=I+try+to+make+a+application+with+python+and+kivy+for+Android+and+Ios+but+in+build+cause+erorrs+so+I+\">Project Stats</a>"));
    }

    /**
     * testprojectStats: Test of projectStats method, of SearchController.
     */
    @Test
    public void testprojectStats() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(Helpers.GET)
                .uri("/projectStat?prj=El+proyecto+consiste+en+realizar+algoritmos+de+ML+siguiendo+la+estructura+planteada+en+la+PPT.+La+da");
        Result result = Helpers.route(app, request);
        assertEquals(Http.Status.OK, result.status());
        String html = Helpers.contentAsString(result);
        assertTrue(html.contains("<p>{siguiendo=1, de=1, algoritmos=1, realizar=1, el=1, en=2, proyecto=1, ppt.=1, consiste=1, la=3, planteada=1, estructura=1, da=1, ml=1}</p>"));
//        assertTrue(html.contains("<li>Count: 19</li>"));
    }

    /**
     * testglobalStats: Test of globalStats method, of SearchController.
     */
    @Test
    public void testglobalStats() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(Helpers.GET).uri("/globalStats?keyword=python+developers");
        Result result = Helpers.route(app, request);
        assertEquals(Http.Status.OK,result.status());
        String html = Helpers.contentAsString(result);
        assertTrue(html.contains("<p>{to=63, a=56, python=38, the=35, for=29, need=28, and=28, in=24, i=21, of=18, is=16, looking=15, developers=14, developer=13, on=13, with=11, an=10, have=10, that=10, using=10, will=10, am=9, work=9, code=8, app=8, project=8, we=8, data=8, from=8, be=7, can=7, me=6, our=6, web=6, like=6, do=5, experienced=5, or=5, application=5, but=5, android=5, not=5, software=5, help=5, according=5, build=5, would=4, website=4, some=4, mobile=4, algorithm=4, server=4, my=4, nextjs=4, want=4, are=4, so=4, develop=4, hire=4, experience=4, few=4, who=4, you=3, search=3, c=3, de=3, which=3, en=3, existing=3, years=3, if=3, im=3, it=3, product=3, la=3, use=3, all=3, basis=3, based=3, javascript=3, creating=3, odoo=3, dataset=3, has=3, please=3, description=3, get=3, small=3, create=3, development=3, game=3, someone=3, german=2, stack=2, projects=2, year=2, remote=2, ios=2, rank=2, revolutionize=2, excellent=2, require=2, senior=2, example=2, coding=2, systems=2, term=2, executable=2, days=2, y=2, fullstack=2, spider=2, trail=2, startup=2, implement=2, programming=2, add=2, needs=2, test=2, its=2, users=2, vision=2, neural=2, germany=2, related=2, uml=2, company=2, live=2, custom=2, multiple=2, parse=2, expert=2, number=2, join=2, text=2, programmer=2, index=2, script=2, status=2, js=2, file=2, daytoday=2, able=2, report=2, points=2, read=2, task=2, were=2, monthly=2, bot=2, redm=2, make=2, allows=2, social=2, any=2, java=2, must=2, appi=2, automatically=2, long=2, into=2, think=2, plugin=2, bug=2, open=2, source=2, accounting=2, research=2, features=2, ug=2, un=2, offers=2, given=2, engineering=2, flutter=2, name=2, full=2, lidar=2, apply=2, start=2, helloi=2, dapp=2, interns=2, heidelberg=2, following=2, smart=2, graphql=2, include=2, travel=2, simple=2, constantly=2, responsibilities=2, ver=2, send=2, line=2, asap=2, network=2, candidate=2, built=2, various=2, lua=2, front=2, frontend=2, webadd=1, requiredexperience=1, photowe=1, upload=1, httpsinteractivebrokersgithwe=1, your=1, building=1, applyingif=1, imputation=1, experienceroles=1, expertodoo=1, appdesigns=1, blogging=1, httpswwwstreampredatoronlinei=1, carefully=1, androidios=1, version=1, ar=1, divulgado=1, algonecesito=1, as=1, size=1, opthis=1, left=1, oldhtmls=1, projecreate=1, strong=1, algoritmos=1, websitegodaddy=1, proyecto=1, talking=1, apistart=1, immediately=1, balanced=1, sea=1, decentralised=1, same=1, selenium=1, by=1, solurion=1, dneeds=1, deadline=1, partnr=1, embedded=1, resume=1, databasepython=1, business=1, kernel=1, jobs=1, right=1, andor=1, q=1, complicated=1, spin=1, information=1, yrs=1, interview=1, trbackpacker=1, coulda=1, parses=1, vendan=1, developed=1, crminimum=1, analytics=1, tie=1, scratchhiwe=1, kivy=1, sewe=1, subscrintroductionembedded=1, raspberi=1, qrack=1, el=1, customersupzoomrx=1, check=1, teamdjango=1, immediate=1, fa=1, technical=1, roles=1, paste=1, title=1, descriptionan=1, laptopb=1, random=1, producing=1, provides=1, enginerei=1, lines=1, httpswwwotcmarketscomstockgzicoverviewand=1, gi=1, developersim=1, identify=1, perform=1, service=1, well=1, avoid=1, igolang=1, very=1, smaller=1, posps=1, doesnt=1, anonymisation=1, output=1, requerimosi=1, ib=1, portal=1, made=1, testing=1, classification=1, angular=1, scraping=1, driver=1, gui=1, template=1, trading=1, other=1, works=1, clients=1, ping=1, djangopython=1, matter=1, servercustom=1, huffman=1, library=1, world=1, hava=1, followistaking=1, camera=1, urgent=1, modifications=1, side=1, projecexpert=1, getcodetaijob=1, within=1, experencewe=1, cssjava=1, draw=1, clearing=1, url=1, machine=1, planteada=1, constraint=1, edificio=1, codes=1, con=1, wwe=1, soc=1, recognition=1, sign=1, rating=1, wordprei=1, webbased=1, renpy=1, download=1, carefuwant=1, find=1, publication=1, visualize=1, function=1, spunwe=1, mi=1, stock=1, selected=1, algorithmand=1, ml=1, traffic=1, counting=1, new=1, codigo=1, apii=1, level=1, everyone=1, real=1, dpython=1, feed=1, improve=1, prediction=1, try=1, ni=1, freelancer=1, no=1, passionate=1, hiring=1, specificyou=1, cause=1, apiel=1, media=1, contracts=1, lineed=1, heroku=1, talented=1, working=1, backend=1, trselected=1, brief=1, comments=1, looked=1, torequirement=1, pytesseract=1, interestedwant=1, mern=1, rpi=1, databasecreate=1, consiste=1, mustthe=1, due=1, pls=1, pi=1, online=1, until=1, scriptscrape=1, maintain=1, csv=1, about=1, skills=1, lambda=1, worked=1, edits=1, above=1, onlypartnr=1, shello=1, kotlinusing=1, let=1, api=1, interchange=1, specifichellolooking=1, backendi=1, streamlit=1, summetry=1, hoselected=1, countries=1, each=1, quality=1, matplotlib=1, experienwere=1, items=1, httpswwwtirexoartlinki=1, logics=1, lone=1, students=1, believe=1, desarrollar=1, toointeractive=1, jupyter=1, found=1, vendor=1, takes=1, arm=1, unreal=1, candycrush=1, email=1, latest=1, genetic=1, everydayplease=1, isdata=1, consulting=1, team=1, opencvonion=1, developerintroductionembedded=1, te=1, face=1, pictureswe=1, ppt=1, progress=1, pharma=1, experts=1, through=1, functionlities=1, cousing=1, newrite=1, wordpress=1, fuente=1, metaverses=1, run=1, dynamic=1, written=1, unbalanced=1, generate=1, sector=1, tech=1, busplease=1, this=1, textgame=1, nebackpacker=1, medias=1, extract=1, discord=1, blockchain=1, once=1, solve=1, skiladd=1, fields=1, updated=1, que=1, next=1, installed=1, transmia=1, alli=1, inswe=1, detect=1, learning=1, pythoni=1, cold=1, customisations=1, video=1, speed=1, fixs=1, creacin=1, param=1, linux=1, expertwe=1, teach=1, was=1, technology=1, windows=1, localizacin=1, detailed=1, time=1, dahelloi=1, pets=1, studio=1, youll=1, realizar=1, overhauled=1, interactive=1, projectdear=1, thisyou=1, react=1, program=1, repository=1, revolution=1, when=1, required=1, multi=1, problem=1, scans=1, enter=1, dashboard=1, tenant=1, between=1, platformwould=1, give=1, images=1, roads=1, pleasthis=1, innovative=1, programmingfor=1, coswe=1, provide=1, saas=1, estructura=1, word=1, care=1, stackoverfadd=1, brokers=1, scincr=1, erorrs=1, otwe=1, crwe=1, low=1, toucapp=1, requirementspbuilt=1, tutors=1, initial=1, efficient=1, more=1, proi=1, lead=1, andwe=1, diagramshiim=1, scince=1, chat=1, fetch=1, responses=1, randomly=1, minimum=1, objectoriented=1, pythdeadline=1, thru=1, satiwork=1, datathere=1, preferably=1, before=1, wondering=1, shows=1, responsib=1, cologne=1, notebook=1, marketplace=1, camerawe=1, fileb=1, tau=1, commitment=1, published=1, done=1, eclipse=1, both=1, este=1, market=1, daily=1, senpython=1, managing=1, job=1, comprobar=1, youtube=1, siguiendo=1, ease=1, link=1, their=1, platform=1, requirementsadd=1, security=1, paper=1, para=1, ready=1, designed=1, clean=1, django=1, diagramscreate=1, accountingpostgresql=1, user=1, xls=1, gpio=1}</p>"));
    }

    /**
     * testprojectsIncludingSkill: Test of projectsIncludingSkill method, of SearchController.
     */
    @Test
    public void testprojectsIncludingSkill(){
        Http.RequestBuilder request = new Http.RequestBuilder().method(Helpers.GET).uri("/projectsIncludingSkill?id=116&skill=Software+Architecture");
        Result result = Helpers.route(app, request);
        assertEquals(Http.Status.OK,result.status());
        String html = Helpers.contentAsString(result);
        assertTrue(html.contains("<a href=\"/profile/20137556\">20137556</a>"));
    }

    /**
     * testreadability: Test of readability method, of SearchController.
     */
    @Test
    public void testreadability(){
        Http.RequestBuilder request = new Http.RequestBuilder().method(Helpers.GET).uri("/readability/I%20need%20a%20custom%20discord%20text-game%20bot%20for%20our%20server.%0ACustom%20functionlities,%20written%20in%20Python.%0A%0A-%207");
        Result result = Helpers.route(app, request);
        assertEquals(Http.Status.OK,result.status());
        String html = Helpers.contentAsString(result);
        assertTrue(html.contains("<p>INDEX STATISTICS</p>"));
    }

    /**
     * testprofileInfo: Test of profileInfo method, of SearchController.
     */    
    @Test
    public void testprofileInfo(){
        Http.RequestBuilder request = new Http.RequestBuilder().method(Helpers.GET).uri("/profile/61332005");
        Result result = Helpers.route(app, request);
        assertEquals(Http.Status.OK,result.status());
        String html = Helpers.contentAsString(result);
        assertTrue(html.contains("<h2> Armannikseresht</h2>"));
    }
}