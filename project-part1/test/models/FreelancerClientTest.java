package models;

import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import org.mockito.Mockito;
import play.cache.AsyncCacheApi;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Class GithubClientTest
 * @author Hop Nguyen
 */
public class FreelancerClientTest {

    /**
     * @param cachedValue
     * @return a mocked cache api for testing
     * @author Hop Nguyen
     */
    private AsyncCacheApi mockCache(Object cachedValue) {
        AsyncCacheApi cache = mock(AsyncCacheApi.class);
        when(cache.getOrElseUpdate(anyString(), any())).thenAnswer(params -> {
            if (cachedValue != null) {
                return cachedValue;
            }
            final Callable<?> provider = params.getArgument(1);
            return provider.call();
        });
        when(cache.getOrElseUpdate(anyString(), any(), anyInt())).thenAnswer(params -> {
            if (cachedValue != null) {
                return cachedValue;
            }
            final Callable<?> provider = params.getArgument(1);
            return provider.call();
        });
        return cache;
    }

    /**
     * This is to test the repositories search
     *
     * @author Hop Nguyen
     */
    @Test
    public void testSearchProjects() throws Exception {
        WSClient client = mock(WSClient.class);
        WSRequest request = mock(WSRequest.class);
        WSResponse response = mock(WSResponse.class);

        when(client.url("https://www.freelancer.com/api/projects/0.1/projects/active")).thenReturn(request);
        when(request.addHeader(eq("Authorization"), anyString())).thenReturn(request);
        when(request.addQueryParameter("query", "python developers")).thenReturn(request);
        when(request.addQueryParameter("compact", "false")).thenReturn(request);
        when(request.addQueryParameter("limit", "10")).thenReturn(request);
        when(request.addQueryParameter("job_details", "true")).thenReturn(request);
        when(request.get()).thenReturn(CompletableFuture.completedFuture(response));
        String responseString = "{\n" +
                "    \"status\": \"success\",\n" +
                "    \"result\": {\n" +
                "        \"projects\": [{\n" +
                "            \"id\": 33259375,\n" +
                "            \"owner_id\": 1542,\n" +
                "            \"title\": \"Order book and Trade Book Management NSE using Python API\",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"python/Order-book-Trade-Book-Management\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 13,\n" +
                "                \"name\": \"Python\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"python\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647831020,\n" +
                "            \"preview_description\": \"Automate placing orders in a price band.\\nAutomating placing target orders and stop loss orders as an\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 10.0,\n" +
                "                \"maximum\": 30.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 1,\n" +
                "                \"bid_avg\": 150.0\n" +
                "            },\n" +
                "            \"time_submitted\": 1647831020,\n" +
                "            \"time_updated\": 1647831020,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647826539,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33258936,\n" +
                "            \"owner_id\": 61332005,\n" +
                "            \"title\": \"Custom discord game bot \",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"python/Custom-discord-game-bot\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 9,\n" +
                "                \"code\": \"CAD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"Canadian Dollar\",\n" +
                "                \"exchange_rate\": 0.793452,\n" +
                "                \"country\": \"CA\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": false\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"PHP\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"php\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 13,\n" +
                "                \"name\": \"Python\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"python\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 59,\n" +
                "                \"name\": \"Android\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 9,\n" +
                "                    \"name\": \"Mobile Phones & Computing\"\n" +
                "                },\n" +
                "                \"seo_url\": \"android\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 116,\n" +
                "                \"name\": \"Software Architecture\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"software_architecture\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 2657,\n" +
                "                \"name\": \"Discord\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 101,\n" +
                "                    \"name\": \"Telecommunications\"\n" +
                "                },\n" +
                "                \"seo_url\": \"discord\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647827121,\n" +
                "            \"preview_description\": \"I need a custom discord text-game bot for our server.\\nCustom functionlities, written in Python.\\n\\n- 7\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 250.0,\n" +
                "                \"maximum\": 750.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 6,\n" +
                "                \"bid_avg\": 741.6666666666666\n" +
                "            },\n" +
                "            \"time_submitted\": 1647827121,\n" +
                "            \"time_updated\": 1647827121,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647821016,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33259106,\n" +
                "            \"owner_id\": 43918440,\n" +
                "            \"title\": \"Portal Streamlit y Pyhton para comprobar la localizaci\\u00f3n de un edificio\",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"python/Portal-Streamlit-Pyhton-para-comprobar\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 8,\n" +
                "                \"code\": \"EUR\",\n" +
                "                \"sign\": \"\\u20ac\",\n" +
                "                \"name\": \"Euro\",\n" +
                "                \"exchange_rate\": 1.10491,\n" +
                "                \"country\": \"EU\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 9,\n" +
                "                \"name\": \"JavaScript\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"javascript\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 13,\n" +
                "                \"name\": \"Python\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"python\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 335,\n" +
                "                \"name\": \"HTML\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"html\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647827061,\n" +
                "            \"preview_description\": \"Creaci\\u00f3n de Portal con Streamlit y Python para comprobar  localizaci\\u00f3n de un edificio. Requerimos:\\n-\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 250.0,\n" +
                "                \"maximum\": 750.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 11,\n" +
                "                \"bid_avg\": 481.72727272727275\n" +
                "            },\n" +
                "            \"time_submitted\": 1647827061,\n" +
                "            \"time_updated\": 1647827061,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"es\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647827181,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33258953,\n" +
                "            \"owner_id\": 32741419,\n" +
                "            \"title\": \"Spin Text to Text Software (C#/Pathon)\",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"python/Spin-Text-Text-Software-Pathon\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 13,\n" +
                "                \"name\": \"Python\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"python\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 106,\n" +
                "                \"name\": \"C# Programming\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"c_sharp_programming\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647825058,\n" +
                "            \"preview_description\": \"I am looking for a simple program to paste spin text on the left.  The right side will have the spun\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 10.0,\n" +
                "                \"maximum\": 30.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 5,\n" +
                "                \"bid_avg\": 67.0\n" +
                "            },\n" +
                "            \"time_submitted\": 1647825058,\n" +
                "            \"time_updated\": 1647825058,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647821243,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33258877,\n" +
                "            \"owner_id\": 61331943,\n" +
                "            \"title\": \"Game developer\",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"3d-modelling/Game-developer-33258877\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 93,\n" +
                "                \"name\": \"3D Rendering\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 3,\n" +
                "                    \"name\": \"Design, Media & Architecture\"\n" +
                "                },\n" +
                "                \"seo_url\": \"rendering\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 394,\n" +
                "                \"name\": \"3D Modelling\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 3,\n" +
                "                    \"name\": \"Design, Media & Architecture\"\n" +
                "                },\n" +
                "                \"seo_url\": \"3d_modelling\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 395,\n" +
                "                \"name\": \"3D Animation\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 3,\n" +
                "                    \"name\": \"Design, Media & Architecture\"\n" +
                "                },\n" +
                "                \"seo_url\": \"3d_animation\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 668,\n" +
                "                \"name\": \"Game Development\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"game_development\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 1313,\n" +
                "                \"name\": \"Unreal Engine\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"unreal_engine\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647823614,\n" +
                "            \"preview_description\": \"We are in the search of game developers or a studio to build an open world game on unreal engine\\n\\nRe\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"hourly\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 50.0\n" +
                "            },\n" +
                "            \"hourly_project_info\": {\n" +
                "                \"commitment\": {\n" +
                "                    \"hours\": 40,\n" +
                "                    \"interval\": \"week\"\n" +
                "                },\n" +
                "                \"duration_enum\": \"unspecified\"\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 5,\n" +
                "                \"bid_avg\": 50.0\n" +
                "            },\n" +
                "            \"time_submitted\": 1647823614,\n" +
                "            \"time_updated\": 1647823614,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647819637,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33258530,\n" +
                "            \"owner_id\": 61234556,\n" +
                "            \"title\": \"Looking for a SQA expert\",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"python/Looking-for-SQA-expert\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 7,\n" +
                "                \"name\": \"Java\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"java\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 9,\n" +
                "                \"name\": \"JavaScript\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"javascript\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 13,\n" +
                "                \"name\": \"Python\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"python\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 167,\n" +
                "                \"name\": \"Software Testing\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"software_testing\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 208,\n" +
                "                \"name\": \"Test Automation\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"test_automation\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647819340,\n" +
                "            \"preview_description\": \"I want someone to work on a daily basis quality testing task. Java, python required. Selenium and ot\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"hourly\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 2.0,\n" +
                "                \"maximum\": 8.0\n" +
                "            },\n" +
                "            \"hourly_project_info\": {\n" +
                "                \"commitment\": {\n" +
                "                    \"hours\": 40,\n" +
                "                    \"interval\": \"week\"\n" +
                "                },\n" +
                "                \"duration_enum\": \"unspecified\"\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 3,\n" +
                "                \"bid_avg\": 11.666666666666666\n" +
                "            },\n" +
                "            \"time_submitted\": 1647819340,\n" +
                "            \"time_updated\": 1647819340,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647815740,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33258448,\n" +
                "            \"owner_id\": 1418936,\n" +
                "            \"title\": \"Maintain multiple versions of same product in GIT/Bitbucket \",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"git/Maintain-multiple-versions-same-product\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 116,\n" +
                "                \"name\": \"Software Architecture\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"software_architecture\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 741,\n" +
                "                \"name\": \"Git\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"git\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 1197,\n" +
                "                \"name\": \"Jenkins\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"jenkins\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 1267,\n" +
                "                \"name\": \"Version Control Git\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"version_control_git\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 1678,\n" +
                "                \"name\": \"DevOps\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 5,\n" +
                "                    \"name\": \"Engineering & Science\"\n" +
                "                },\n" +
                "                \"seo_url\": \"devops\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647818179,\n" +
                "            \"preview_description\": \"We maintain multiple version of same software code for example \\nVer 1.2.1  =  Bug Fix\\u2019s  \\nVer 1.2.2 \",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": true,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 30.0,\n" +
                "                \"maximum\": 250.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 4,\n" +
                "                \"bid_avg\": 157.5\n" +
                "            },\n" +
                "            \"time_submitted\": 1647818179,\n" +
                "            \"time_updated\": 1647818179,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": true,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647824179,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33258244,\n" +
                "            \"owner_id\": 22860600,\n" +
                "            \"title\": \"Desktop and Mobile Accounting APP - Lavarel 8\",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"php/Desktop-Mobile-Accounting-APP-Lavarel\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"PHP\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"php\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 67,\n" +
                "                \"name\": \"Testing / QA\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"testing_qa\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 951,\n" +
                "                \"name\": \"App Developer\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"app_developer\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 1126,\n" +
                "                \"name\": \"Vue.js\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"vue_js\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 1933,\n" +
                "                \"name\": \"Development\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"development\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 1992,\n" +
                "                \"name\": \"Financial Software Development\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"financial_software_development\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 2382,\n" +
                "                \"name\": \"Web Application\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"web_application\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647816000,\n" +
                "            \"preview_description\": \"We are building an accounting  web APP designed well so it would be ease of use on both Mobile and D\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 2000.0,\n" +
                "                \"maximum\": 3000.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 21,\n" +
                "                \"bid_avg\": 2642.8571428571427\n" +
                "            },\n" +
                "            \"time_submitted\": 1647816000,\n" +
                "            \"time_updated\": 1647816000,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": true,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647812364,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33258175,\n" +
                "            \"owner_id\": 54589659,\n" +
                "            \"title\": \"Scrape dynamic stock data \",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"python/Scrape-dynamic-stock-data\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 13,\n" +
                "                \"name\": \"Python\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"python\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 95,\n" +
                "                \"name\": \"Web Scraping\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"web_scraping\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 305,\n" +
                "                \"name\": \"MySQL\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"mysql\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647815124,\n" +
                "            \"preview_description\": \"Python script:\\nScrape dynamic stock data from: https://www.otcmarkets.com/stock/GZIC/overview\\nand se\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 10.0,\n" +
                "                \"maximum\": 30.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 15,\n" +
                "                \"bid_avg\": 36.333333333333336\n" +
                "            },\n" +
                "            \"time_submitted\": 1647815124,\n" +
                "            \"time_updated\": 1647815124,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647811496,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }, {\n" +
                "            \"id\": 33257838,\n" +
                "            \"owner_id\": 48049675,\n" +
                "            \"title\": \"Reverse ARM file find Algorithm of encryption -- 3\",\n" +
                "            \"status\": \"active\",\n" +
                "            \"seo_url\": \"c-programming/Reverse-ARM-file-find-Algorithm\",\n" +
                "            \"currency\": {\n" +
                "                \"id\": 1,\n" +
                "                \"code\": \"USD\",\n" +
                "                \"sign\": \"$\",\n" +
                "                \"name\": \"US Dollar\",\n" +
                "                \"exchange_rate\": 1.0,\n" +
                "                \"country\": \"US\",\n" +
                "                \"is_external\": false,\n" +
                "                \"is_escrowcom_supported\": true\n" +
                "            },\n" +
                "            \"jobs\": [{\n" +
                "                \"id\": 6,\n" +
                "                \"name\": \"C Programming\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"c_programming\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 7,\n" +
                "                \"name\": \"Java\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"java\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 31,\n" +
                "                \"name\": \"Linux\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"linux\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 59,\n" +
                "                \"name\": \"Android\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 9,\n" +
                "                    \"name\": \"Mobile Phones & Computing\"\n" +
                "                },\n" +
                "                \"seo_url\": \"android\",\n" +
                "                \"local\": false\n" +
                "            }, {\n" +
                "                \"id\": 2044,\n" +
                "                \"name\": \"Reverse Engineering\",\n" +
                "                \"category\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"name\": \"Websites, IT & Software\"\n" +
                "                },\n" +
                "                \"seo_url\": \"reverse_engineering\",\n" +
                "                \"local\": false\n" +
                "            }],\n" +
                "            \"submitdate\": 1647814522,\n" +
                "            \"preview_description\": \"we have some executable ARM Soc Android Vendor kernel driver, \\nwe need find solurion for that, pleas\",\n" +
                "            \"deleted\": false,\n" +
                "            \"nonpublic\": false,\n" +
                "            \"hidebids\": false,\n" +
                "            \"type\": \"fixed\",\n" +
                "            \"bidperiod\": 7,\n" +
                "            \"budget\": {\n" +
                "                \"minimum\": 5000.0,\n" +
                "                \"maximum\": 10000.0\n" +
                "            },\n" +
                "            \"featured\": false,\n" +
                "            \"urgent\": false,\n" +
                "            \"bid_stats\": {\n" +
                "                \"bid_count\": 5,\n" +
                "                \"bid_avg\": 9940.0\n" +
                "            },\n" +
                "            \"time_submitted\": 1647814522,\n" +
                "            \"time_updated\": 1647814522,\n" +
                "            \"upgrades\": {\n" +
                "                \"featured\": false,\n" +
                "                \"sealed\": false,\n" +
                "                \"nonpublic\": false,\n" +
                "                \"fulltime\": false,\n" +
                "                \"urgent\": false,\n" +
                "                \"qualified\": false,\n" +
                "                \"NDA\": false,\n" +
                "                \"ip_contract\": false,\n" +
                "                \"non_compete\": false,\n" +
                "                \"project_management\": false,\n" +
                "                \"pf_only\": false\n" +
                "            },\n" +
                "            \"language\": \"en\",\n" +
                "            \"hireme\": false,\n" +
                "            \"frontend_project_status\": \"open\",\n" +
                "            \"location\": {\n" +
                "                \"country\": {}\n" +
                "            },\n" +
                "            \"local\": false,\n" +
                "            \"negotiated\": false,\n" +
                "            \"time_free_bids_expire\": 1647808154,\n" +
                "            \"pool_ids\": [\"freelancer\"],\n" +
                "            \"enterprise_ids\": [],\n" +
                "            \"is_escrow_project\": false,\n" +
                "            \"is_seller_kyc_required\": false,\n" +
                "            \"is_buyer_kyc_required\": false,\n" +
                "            \"project_reject_reason\": {}\n" +
                "        }],\n" +
                "        \"total_count\": 463\n" +
                "    },\n" +
                "    \"request_id\": \"20b60203b9f2590295d3fd68b5427930\"\n" +
                "}";
        when(response.asJson()).thenReturn(Json.parse(responseString));
        FreelancerClient freelancer = new FreelancerClient(client, mockCache(null), ConfigFactory.load());
        CompletionStage<SearchResult> future = freelancer.searchProjects("python developers");
        SearchResult searchResult = future.toCompletableFuture().get();
        assertEquals("python developers", searchResult.getInput());
        assertEquals("48252345", searchResult.getProjects().get(0).getOwner());
        assertEquals("Transcription Audio/Video", searchResult.getProjects().get(0).getTitle());
        Mockito.verify(request).addHeader("freelancelotESAPP", "aUzhSBUrlZiSK4o8yQ8CA8ZyJ36VRvh");
        Mockito.verify(request).addQueryParameter("query", "python developers");
        Mockito.verify(request).addQueryParameter("compact", "false");
        Mockito.verify(request).addQueryParameter("job_details", "true");
        Mockito.verify(request).addQueryParameter("limit", "10");
    }
}

