
package io.podarkes;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.start(7000);

        app.get("/games", ctx -> ctx.result(""));
        app.put("/games", ctx -> ctx.result(""));
        app.post("/games", ctx -> ctx.result(""));
        app.get("/games/<gameId>", ctx -> ctx.result(""));

        app.get("/games/<gameId>/moves", ctx -> ctx.result(""));
        app.put("/games/<gameId>/moves", ctx -> ctx.result(""));

        app.get("/games", ctx -> ctx.result(""));
        app.get("/games/<gameId>", ctx -> ctx.result(""));
    }
}
