
<div id="header">
<h1>뿌리기 REST API Guide</h1>
<div id="toc" class="toc2">
<div id="toctitle">뿌리기 REST API</div>
<ul class="sectlevel1">
<li><a href="#overview-http-verbs">HTTP 동사</a></li>
<li><a href="#overview-http-status-codes">HTTP 상태 코드</a></li>
<li><a href="#overview-errors">Exception</a></li>
<li><a href="#api-type">API 목록</a></li>
</ul>
</div>
</div>
<div id="content">
<div class="sect1">
<h2 id="overview-http-verbs"><a class="link" href="#overview-http-verbs">HTTP 동사</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>본 REST API에서 사용하는 HTTP 동사는 가능한한 표준 HTTP와 REST 규약을 따릅니다.</p>
</div>
<table class="tableblock frame-all grid-all spread">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">동사</th>
<th class="tableblock halign-left valign-top">용례</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>GET</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">리소스를 가져올 때 사용</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>POST</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">새 리소스를 만들 때 사용</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>PUT</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기존 리소스를 수정할 때 사용</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>DELETE</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기존 리소스를 삭제할 떄 사용</p></td>
</tr>
</tbody>
</table>
</div>
</div>
<div class="sect1">
<h2 id="overview-http-status-codes"><a class="link" href="#overview-http-status-codes">HTTP 상태 코드</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>본 REST API에서 사용하는 HTTP 상태 코드는 가능한한 표준 HTTP와 REST 규약을 따릅니다.</p>
</div>
<table class="tableblock frame-all grid-all spread">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">상태 코드</th>
<th class="tableblock halign-left valign-top">용례</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>200 OK</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">요청을 성공적으로 처리함</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>201 Created</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">새 리소스를 성공적으로 생성함. 응답의 <code>Location</code> 헤더에 해당 리소스의 URI가 담겨있다.</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>204 No Content</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기존 리소스를 성공적으로 수정함.</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>400 Bad Request</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">잘못된 요청을 보낸 경우. 응답 본문에 더 오류에 대한 정보가 담겨있다.</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>403 Forbidden</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증된 클라이언트가 권한이 없는 자원에 접근할 때 응답하는 상태코드다.</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>404 Not Found</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">요청한 리소스가 없음.</p></td>
</tr>
</tbody>
</table>
</div>
</div>
<div class="sect1">
<h2 id="overview-errors"><a class="link" href="#overview-errors">Exception</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>뿌리기 API에서 발생하는 Exception 종류와 설명이다.</p>
</div>
<table class="tableblock frame-all grid-all spread">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Exception</th>
<th class="tableblock halign-left valign-top">설명</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>RecevieAccessDeniedException</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 및 받기 권한이 없을 때 발생하는 익셉션</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>RequiredParameterNotFoundException</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">필수 파라미터가 존재하지 않을 때 발생하는 익셉션</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>TimeOverException</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 및 받기 기간이 만료되었을 때 발생하는 익셉션</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>TokenNotFoundException</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">토큰이 존재하지 않을 때 발생하는 익셉션</p></td>
</tr>
</tbody>
</table>
</div>
</div>
<div class="sect1">
<h2 id="api-type"><a class="link" href="#api-type">API 목록</a></h2>
<div class="sectionbody">
<table class="tableblock frame-all grid-all spread">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Type</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">설명</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><a href="share/index.html#resources-share">/SHARE</a></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">뿌리기 API</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><a href="receive/index.html#resources-receive">/RECEIVE</a></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">받기 API</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><a href="search/index.html#resources-search">/SEARCH</a></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 API</p></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<div id="footer">
<div id="footer-text">
Last updated 2020-10-11 22:39:38 KST
</div>
</div>




