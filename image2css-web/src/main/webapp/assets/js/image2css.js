var context = function(jq, apiRoot, responseFormatterCallback) {
    var internal;

    internal = {
        "etc": {
            "fadeDelay": 300
        }

        ,"pageElements": {

            "$conversionRequestResponseBody": function() {
                return jq("#responseBody");
            }

            ,"$convertButton": function() {
                return jq("#convertUrl")
            }

            ,"$conversionRequestAwaitingResponse": function() {
                return $("#conversionRequestAwaitingResponse");
            }

            ,"$conversionRequestReceivedResponse": function() {
                return $("#conversionRequestReceivedResponse");
            }

            ,"$result": function() {
                return $("#demo-result");
            }

            ,"$targetUrl": function() {
                return jq("#targetUrl")
            }

        }

        ,"eventHandlers": {
            "resetForRequest": function(callback) {
                internal.pageElements.$result().fadeOut(internal.etc.fadeDelay, function() {
                    internal.pageElements.$conversionRequestReceivedResponse().hide();
                    internal.pageElements.$conversionRequestResponseBody()[0].innerHTML = "";
                    callback();
                });
            }

            ,"requestConversion": function() {
                internal.eventHandlers.resetForRequest(function() {
                    internal.pageElements.$result().fadeIn(internal.etc.fadeDelay,
                        internal.eventHandlers.conversionRequestStatusIndicator(true, internal.transport.getConversion()
                    ));
                });
            }

            ,"requestConversionDisplaySuccessResponse": function(data) {
                var responseContent = responseFormatterCallback ? responseFormatterCallback(data.body) : data.body;
                internal.pageElements.$conversionRequestResponseBody()[0].innerHTML = responseFormatterCallback(responseContent);
            }

            ,"requestConversionDisplayFailureResponse": function(data) {
                var message = JSON.parse(data.responseText).message;
                internal.pageElements.$conversionRequestResponseBody()[0].innerHTML = message;
            }

            ,"urlEnterHandler": function(event) {
                event.which === 13 ? internal.eventHandlers.requestConversion() : undefined;
            }

            ,"conversionRequestStatusIndicator": function(waiting, callback) {
                if(waiting) {
                    internal.pageElements.$conversionRequestReceivedResponse().fadeOut(internal.etc.fadeDelay, function() {
                        internal.pageElements.$conversionRequestAwaitingResponse().fadeIn(internal.etc.fadeDelay, callback);
                    });
                }else {
                    internal.pageElements.$conversionRequestAwaitingResponse().fadeOut(internal.etc.fadeDelay, function() {
                        internal.pageElements.$conversionRequestReceivedResponse().hide();
                        internal.pageElements.$conversionRequestReceivedResponse().removeClass("hidden");
                        internal.pageElements.$conversionRequestReceivedResponse().fadeIn(internal.etc.fadeDelay);
                    });
                }
            }

        }

        ,"transport": {
            "routes": {
                "conversionService" : function(queryParamImageUrl) {
                    return apiRoot + "/convert?url=" + queryParamImageUrl;
                }
            }

            ,"getConversion": function() {
                var targetUrlValue = internal.pageElements.$targetUrl()[0].value;

                $.get(internal.transport.routes.conversionService(targetUrlValue))
                    .done(internal.eventHandlers.requestConversionDisplaySuccessResponse)
                    .fail(internal.eventHandlers.requestConversionDisplayFailureResponse)
                    .always(function() {
                        internal.eventHandlers.conversionRequestStatusIndicator(false);
                    });
            }

        }

        ,"registerEventHandlers": function() {
            internal.pageElements.$convertButton().click(internal.eventHandlers.requestConversion);
            internal.pageElements.$targetUrl().keyup(internal.eventHandlers.urlEnterHandler);
        }
    };

    jq(document).ready(function() {
        internal.registerEventHandlers();
    });

    return {};

};


