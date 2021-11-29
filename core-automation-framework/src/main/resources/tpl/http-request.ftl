<html>
<#-- @ftlvariable name="data" type="cz.inventi.qa.framework.core.allure.attachments.HttpRequestAttachment" -->
    <head>
        <meta http-equiv="content-type" content="text/html; charset = UTF-8">
        <style>
            pre {
                white-space: pre-wrap;
            }
        </style>
    </head>
    <body>
        <#if data??>
            <div>
                <pre><code><#if data.method??>${data.method}<#else>GET</#if> <#if data.url??>${data.url}<#else>Unknown</#if></code></pre>
            </div>

            <#if data.formParams?has_content>
                <h4>Form Params</h4>
                <div>
                    <#list data.formParams as name, value>
                        <pre><code><b>${name}</b>: ${value}</code></pre>
                    </#list>
                </div>
            </#if>

            <#if data.body??>
                <h4>Body</h4>
                <div>
                    <pre><code>${data.body}</code></pre>
                </div>
            </#if>

            <#if (data.headers)?has_content>
                <h4>Headers</h4>
                <div>
                    <#list data.headers as nameAndValue>
                        <pre><code><b>${nameAndValue}</code></pre>
                    </#list>
                </div>
            </#if>

            <#if (data.authentication.authenticationSpecs)?has_content>
                <h4>Authentication (${data.authentication.authenticationName})</h4>
                <div>
                    <#list data.authentication.authenticationSpecs as name, value>
                        <pre><code><b>${name}</b>: ${value}</code></pre>
                    </#list>
                </div>
            </#if>

            <#if (data.cookies)?has_content>
                <h4>Cookies</h4>
                <div>
                    <#list data.cookies as name, value>
                        <div>
                            <pre><code><b>${name}</b>: ${value}</code></pre>
                        </div>
                    </#list>
                </div>
            </#if>

            <#if data.curl??>
                <h4>Curl</h4>
                <div>
                    <pre><code>${data.curl}</code></pre>
                </div>
            </#if>
        <#else>No request data saved.</#if>
    </body>
</html>