<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${webSite.websiteTitle}</title>
    <meta name="keywords" content="${webSite.websiteKeyword}">
    <meta name="description" content="${webSite.websiteDesc}">
<#include "*/common.ftl">
</head>
<body>
<div id="wrapper">
<#if customDirectives.isEnglish>
    dd
<#else>
    订单
</#if>
    <!--head start-->
    <div id="head">
    ${customDirectives.loadModule("top",extMap)}
    ${customDirectives.loadModule("slide",extMap)}
    </div>
    <!--head end-->
    <!--body start-->
    <div id="body">
        <!--MainBlock start-->
        <div class="MainBlock">
            <!--left start-->
            <div class="right">
                <div class="topic">
                    <div class="TopicTitle"><a href="">关于公司</a></div>
                    <div class="TopicMore"><a href=""><img
                            src="${resourceRoot}/door/front/skins/template_01/images/more.png"></a></div>
                </div>
                <div class="img"><a href="" target="_blank"><img
                        src="${resourceRoot}/door/front/skins/template_01/images/com.jpg"
                        width="200" height="100" alt="关于公司"></a></div>
                <div class="txt ColorLink"><p>
                    　　某某有限公司致力于XX业的发展，专业从事集中润滑系统的研究、开发、制造及销售，凭借公司强大的技术力量和经济实力，不断开发出具有国际先进技术水平的新产品。公司产品广泛适用于数控机...<a
                        href="" target="_blank">详细&gt;&gt;</a></p></div>
                <div class=" clearfix"></div>
            </div>
            <!--left end-->
            <div class="WidthTab2"></div>

            <!--right start-->
            <div class="left">
                <div class="topic">
                    <div class="TopicTitle"><a href="/News">新闻动态</a></div>
                    <div class="TopicMore"><a href="/News"><img
                            src="${resourceRoot}/door/front/skins/template_01/images/more.png"></a></div>
                </div>
                <div class="clearfix"></div>
                <ul></ul>
                <table class="MBlockTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr>
                        <td width="75%" class="ListTitle">
                            <a href="" target="_blank" title="中国电信下月联合经销商采购千万部智能手机">
                                中国电信下月联合经销商采购千万部智能手机
                            </a>
                        </td>
                        <td width="25%"><span>2012 / 5 / 23</span></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!--right end-->

            <!--right2 start-->
            <div class="right2">
                <div class="topic">
                    <div class="TopicTitle"><a href="/">下载中心</a></div>
                    <div class="TopicMore"><a href="/"><img
                            src="${resourceRoot}/door/front/skins/template_01/images/more.png"></a></div>
                </div>
                <div class="txt ColorLink">
                    <table class="MBlockTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td width="80%" class="ListTitle">
                                <a href="" target="_blank" title="">
                                    中国电信下月
                                </a>
                            </td>
                            <td width="20%"><span>下载</span></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class=" clearfix"></div>
            </div>
            <!--right2 end-->
            <div class="clearfix"></div>

        </div>
        <!--MainBlock end-->

        <div class="HeightTab2 clearfix"></div>

        <!--MainBlock3 start-->
        <div class="MainBlock3">
            <!--right start-->
            <div class="left">
                <div class="TabBlock">
                    <div class="menu">
                        <ul>
                            <li class="Tabs1"><a onmousemove="easytabs(1, 1);" onfocus="easytabs(1, 1);" title="数码播放器"
                                                 id="tablink1" href="Product/DigitalPlayer"
                                                 class="tab1 tabactive">数码播放器</a></li>
                        </ul>
                    </div>
                    <div id="tabcontent1" style="display: block;">
                        <div class="blk_29">
                            <div class="LeftBotton" id="LeftArr1"></div>
                            <div class="Cont" id="ISL_Cont_1" style="width: 888px; overflow: hidden;">
                                <div style="overflow: hidden; zoom: 1; width: 32766px;">
                                    <div style="float: left;">
                                        <div class="box"><a class="imgBorder" href="/Product/9854172030.html"
                                                            target="_blank" title="艾诺 高清大屏MP4触摸+按键"><img
                                                alt="艾诺 高清大屏MP4触摸+按键" src="/images/up_images/20111210171953.jpg">

                                            <p>艾诺 高清大屏MP4触摸+按</p></a></div>

                                    </div>
                                </div>
                            </div>
                            <div class="RightBotton" id="RightArr1"></div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div id="tabcontent2" style="display: none;">
                        <div class="blk_29">
                            <div class="LeftBotton" id="LeftArr2"></div>
                            <div class="Cont" id="ISL_Cont_2" style="width: 888px; overflow: hidden;">

                            </div>
                            <div class="RightBotton" id="RightArr2"></div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            <!--right end-->
            <div class="clearfix"></div>
        </div>
        <!--MainBlock end-->
        <!--links start-->
    <#--<div id="Links">-->
    <#--<span>友情链接：</span>-->
    <#--<a href="http://www.baidu.com" target="_blank" rel="nofollow">百度</a><a-->
    <#--</div>-->
        <!--links end-->

    </div>
    <!--body end-->
${customDirectives.loadModule("bottom",extMap)}

</div>
</body>
</html>
