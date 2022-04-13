package controllers;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import akka.NotUsed;
import org.slf4j.Logger;

import actors.Messages;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;
import play.libs.F.Either;
import play.mvc.*;
import scala.concurrent.duration.Duration;
import scala.compat.java8.FutureConverters;
import static akka.pattern.Patterns.ask;


public class WebSocketController extends Controller {
    private final Timeout t = new Timeout(Duration.create(10, TimeUnit.SECONDS));
    // private final Logger logger = org.slf4j.LoggerFactory.getLogger("controllers.WebSocketController");
    final Logger logger = org.slf4j.LoggerFactory.getLogger("play");

    private final ActorRef userParentActor;

    @Inject
    public WebSocketController(@Named("userParentActor") ActorRef userParentActor) {
        this.userParentActor = userParentActor;
    }

    public WebSocket ws() {
        return WebSocket.Json.acceptOrResult(request -> {
            if (sameOriginCheck(request)) {
                logger.info("Websocket initialized");
                final CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> future = wsFutureFlow(request);
                final CompletionStage<Either<Result, Flow<JsonNode, JsonNode, ?>>> stage = future.thenApply(Either::Right);
                return stage.exceptionally(this::logException);
            } else {
                logger.info("Failed to start websocket");
                return forbiddenResult();
            }
        });
    }

    private Either<Result, Flow<JsonNode, JsonNode, ?>> logException(Throwable throwable) {
        logger.error("Cannot create websocket", throwable);
        Result result = Results.internalServerError("error");
        return Either.Left(result);
    }

    private boolean sameOriginCheck(Http.RequestHeader rh) {
        final Optional<String> origin = rh.header("Origin");

        if (originMatches(origin.get())) {
            logger.debug("originCheck: originValue = " + origin);
            return true;
        } else {
            logger.error("originCheck: rejecting request because Origin header value " + origin + " is not in the same origin");
            return false;
        }
    }

    private boolean originMatches(String origin) {
        return origin.contains("localhost:9000");
    }

    private CompletionStage<Either<Result, Flow<JsonNode, JsonNode, ?>>> forbiddenResult() {
        final Result forbidden = Results.forbidden("forbidden");
        final Either<Result, Flow<JsonNode, JsonNode, ?>> left = Either.Left(forbidden);

        return CompletableFuture.completedFuture(left);
    }

    @SuppressWarnings("unchecked")
    private CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> wsFutureFlow(Http.RequestHeader request) {
        long id = request.asScala().id();
        Messages.UserParentActorCreate create = new Messages.UserParentActorCreate(Long.toString(id));

        return FutureConverters.toJava(ask(userParentActor, create, t)).thenApply((Object flow) -> {
            final Flow<JsonNode, JsonNode, NotUsed> f = (Flow<JsonNode, JsonNode, NotUsed>) flow;
            return f.named("websocket");
        });
    }
}
