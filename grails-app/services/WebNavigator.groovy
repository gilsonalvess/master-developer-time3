import groovyx.net.http.HTTPBuilder


class WebNavigator{
    HTTPBuilder httpBuilder;
    Closure steps;

    WebNavigator(String host){
        httpBuilder = new HTTPBuilder(host)
    }
}