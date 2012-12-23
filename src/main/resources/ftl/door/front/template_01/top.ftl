<!--top start -->
<div class="top">
    <table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
            <td width="350" height="75"><img src="${resourceRoot}/door/front/skins/default/images/logo.gif"
                                             width="320"
                                             height="55"></td>
            <td align="right">
                <table width="180" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr>
                        <td width="100">
                            <a href="javascript:void(0)" id="Chinese">
                                <img src="${resourceRoot}/door/front/skins/default/images/v_cn01.gif" width="80"
                                     height="27" border="0" title="中文版 Chinese"></a>
                        </td>
                        <td>
                            <a href="javascript:void(0)" id="English">
                                <img src="${resourceRoot}/door/front/skins/default/images/v_en01.gif" width="80"
                                     height="27" border="0" title="英文版 English"></a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!--top end-->
<!--nav start-->
<div id="NavLink">
    <div class="NavBG">
        <!--Head Menu Start-->
        <ul id="sddm">
        <#--CurrentLi-->
        <#list channels as channel>
            <li>
                <a href="${channel.channelUrl!}"><#if customDirectives.isEnglish>${channel.channelNameEn!}<#else>${channel.channelName!}<p>${channel.channelNameEn!}</p></#if>
            </a></li>
        </#list>
        </ul>
        <!--Head Menu End-->
    </div>
    <div class="clearfix"></div>
</div>
<!--nav end-->