
<div id="header">
<h1>조회 API</h1>
<div id="toc" class="toc2">
<div id="toctitle">조회 REST API</div>
<ul class="sectlevel1">
<li><a href="https://github.com/shk3029/money/blob/master/index.md">메인 페이지</a></li>
<li><a href="#resources-search">조회 API</a></li>
<li><a href="#_request">Request</a>
<ul class="sectlevel2">
<li><a href="#_request_headers">Request Headers</a></li>
<li><a href="#_request_fields">Request Fields</a></li>
<li><a href="#_http_request">HTTP Request</a></li>
</ul>
</li>
<li><a href="#_response">Response</a>
<ul class="sectlevel2">
<li><a href="#_response_fields">Response Fields</a></li>
<li><a href="#_http_response">HTTP Response</a></li>
</ul>
</li>
</ul>
</div>
</div>
<div id="content">
<div class="sect1">
<h2 id="_메인_페이지"><a class="link" href="#_%EB%A9%94%EC%9D%B8_%ED%8E%98%EC%9D%B4%EC%A7%80">메인 페이지</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>| <a href="https://github.com/shk3029/money/blob/master/index.md">Main</a></p>
</div>
</div>
</div>
<div class="sect1">
<h2 id="resources-search"><a class="link" href="#resources-search">조회 API</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p><code>GET</code> 요청을 사용하여 뿌리기 내역을 조회할 수 있다.</p>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_request"><a class="link" href="#_request">Request</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_request_headers"><a class="link" href="#_request_headers">Request Headers</a></h3>
<table class="tableblock frame-all grid-all spread">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Name</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Accept</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">application/hal+json</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Content-Type</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">application/json</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>X-USER-ID</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">사용자 ID</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>X-ROOM-ID</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">대화방 ID</p></td>
</tr>
</tbody>
</table>
</div>
<div class="sect2">
<h3 id="_request_fields"><a class="link" href="#_request_fields">Request Fields</a></h3>
<table class="tableblock frame-all grid-all spread">
<caption class="title">Table 1. /api/share/{token}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>token</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">토큰</p></td>
</tr>
</tbody>
</table>
</div>
<div class="sect2">
<h3 id="_http_request"><a class="link" href="#_http_request">HTTP Request</a></h3>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code class="language-http hljs" data-lang="http"><span class="hljs-attribute">GET /api/share/nAl HTTP/1.1
Content-Type</span>: application/json
<span class="hljs-attribute">Accept</span>: application/hal+json
<span class="hljs-attribute">X-ROOM-ID</span>: a
<span class="hljs-attribute">X-USER-ID</span>: 1
<span class="hljs-attribute">Host</span>: localhost:8080</code></pre>
</div>
</div>
<hr>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_response"><a class="link" href="#_response">Response</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_response_fields"><a class="link" href="#_response_fields">Response Fields</a></h3>
<table class="tableblock frame-all grid-all spread">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>money</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">뿌린 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">뿌린 시각</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>receivedMoney</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">받기 완료된 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>receivedList</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">받기 완료된 정보[사용자ID, 받은금액]</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links.self.href</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">_links.self</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>_links.profile.href</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">_links.profile</p></td>
</tr>
</tbody>
</table>
</div>
<div class="sect2">
<h3 id="_http_response"><a class="link" href="#_http_response">HTTP Response</a></h3>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code class="language-http hljs" data-lang="http">HTTP/1.1 <span class="hljs-number">200</span> OK
<span class="hljs-attribute">X-USER-ID</span>: 1
<span class="hljs-attribute">X-ROOM-ID</span>: a
<span class="hljs-attribute">Content-Type</span>: application/hal+json
<span class="hljs-attribute">Content-Length</span>: 304

{
  "<span class="hljs-attribute">money" </span>: 10000,
  "<span class="hljs-attribute">receivedMoney" </span>: 0,
  "<span class="hljs-attribute">receivedList" </span>: [ ],
  "<span class="hljs-attribute">createdAt" </span>: "2020-10-11T22:49:49.779027",
  "<span class="hljs-attribute">_links" </span>: {
    "<span class="hljs-attribute">self" </span>: {
      "<span class="hljs-attribute">href" </span>: "http://localhost:8080/api/share/nAl"
    },
    "<span class="hljs-attribute">profile" </span>: {
      "<span class="hljs-attribute">href" </span>: "/docs/search/index.html#resources-search"
    }
  }
}</code></pre>
</div>
</div>
</div>
</div>
</div>
</div>


