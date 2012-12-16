/**
 *版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012
 *日    期： 12-4-7
 *作    者： 朱贤俊
 * 功能：重新编写插入图片插件
 */
var gloEditorImage = null;
KindEditor.plugin('image', function (K) {
    var self = this, name = 'image',
        imgPath = self.pluginsPath + 'image/images/',
        lang = self.lang(name + '.');
    var imagePath = null;
    var preView = null;

    /**
     * 编辑器上传图片
     */
    function uploadEditorImage() {
        $.imageUpload({
            fileElementId:'editorFileImage',
            data:{companyId:companyId, fileSort:"3"},
            successFun:function (data) {
                if (imagePath) {
                    imagePath.val($.ImageUtils.getSrcImageUrl(data.imageId));
                    preView.empty();
                    var _image = $('<img src="' + $.ImageUtils.getImageUrlFor150(data.imageId) + '" alt="预览图"/>').appendTo(preView);
                }
            }
        });
    }

    gloEditorImage = uploadEditorImage;

    self.plugin.imageDialog = function (options) {
        var content = $("<div id='editorContent'></div>");
        var picbox = $("<div class='fl'></div>");
        preView = $('<div style="width: 150px;height: 150px;background: #f5f5f5;border:1px solid #e1e1e1; text-align: center; vertical-align: middle;"></div>');
        var imageBtn = $('<div class="dcen mart_5"><div class="ed-file2" >' +  '上传' +
            '<input id="editorFileImage" type="file" name="fileToUpload" onchange="gloEditorImage()">' +
            '</div></div>');
        picbox.append(preView);
        picbox.append(imageBtn);
        var otherHtml = [
            '<div class="fr"><div class="mart_10">',
            '<label for="editImagePath">' + lang.remoteUrl + '：</label>',
            '<input type="text" name="editImagePath" id="editImagePath" value="" style="width: 225px; padding: 2px;">',
            '</div>',
            //size
            '<div class="mart_10">',
            '<label for="keWidth">' + lang.size + '：</label>',
            lang.width + ' <input style="width: 50px; padding: 2px;" type="text" id="keWidth" name="width" value="" maxlength="4" /> ',
            lang.height + ' <input style="width: 50px; padding: 2px;" type="text" name="height" value="" maxlength="4" /> ',
            '<img class="ke-refresh-btn" style="display: -moz-inline-stack;display: inline-block;vertical-align: middle;zoom: 1;*display: inline;float: none;" src="' + imgPath + 'refresh.png" width="16" height="16" alt="" style="cursor:pointer;" title="' + lang.resetSize + '" />',
            '</div>',
            //align
            '<div class="mart_10">',
            '<label>' + lang.align + '：</label>',
            '<input type="radio" name="align" value="" class="cbox" checked="checked" /> <img name="defaultImg" style="display: -moz-inline-stack;display: inline-block;vertical-align: middle;zoom: 1;*display: inline;float: none;" src="' + imgPath + 'align_top.gif" width="23" height="25" alt="" />&nbsp;&nbsp;',
            '<input type="radio" name="align" value="left" class="cbox"/> <img name="leftImg" style="display: -moz-inline-stack;display: inline-block;vertical-align: middle;zoom: 1;*display: inline;float: none;" src="' + imgPath + 'align_left.gif" width="23" height="25" alt="" />&nbsp;&nbsp;',
            '<input type="radio" name="align" value="right" class="cbox" /> <img name="rightImg" style="display: -moz-inline-stack;display: inline-block;vertical-align: middle;zoom: 1;*display: inline;float: none;" src="' + imgPath + 'align_right.gif" width="23" height="25" alt="" />',
            '</div>',
            //title
            '<div class="mart_10">',
            '<label for="keTitle">' + lang.imgTitle + '：</label>',
            '<input type="text" id="keTitle" style="width: 225px; padding: 2px;" name="title" value=""/>',
            '</div></div>'
        ].join('');

        content.html(otherHtml);
        content.append(picbox);

        var widthBox = content.find('[name="width"]'),
            heightBox = content.find('[name="height"]'),
            refreshBtn = content.find('.ke-refresh-btn'), //重置
            titleBox = content.find('[name="title"]'),
            alignBox = content.find('[name="align"]');
        imagePath = content.find('#editImagePath');

        $.jDialog({//插入图片窗口
            id:"zimageDialog",
            width:"500",
            height:'200',
            title:"插入图片",
            content:content,
            modal:true,
            needAction:true,
            bottomClass:"dright",
            actions:[
                {
                    label:"确 定",
                    actionClasss:'popbtn popbtn01',
                    action:function (dialogObj) {
                        var url = imagePath.val();
                        var title = titleBox.val();
                        var width = widthBox.val();
                        var height = heightBox.val();
                        var border = "0";
                        var align = alignBox.val();

                        alignBox.each(function () {
                            if (this.checked) {
                                align = this.value;
                                return false;
                            }
                        });

                        if (!/^\d*$/.test(width)) {
                            alert(self.lang('invalidWidth'));
                            widthBox[0].focus();
                            return;
                        }
                        if (!/^\d*$/.test(height)) {
                            alert(self.lang('invalidHeight'));
                            heightBox[0].focus();
                            return;
                        }

                        self.exec('insertimage', url, title, width, height, border, align);
                        dialogObj.confirm();
                    }
                }
            ]
        });

        var originalWidth = 0, originalHeight = 0;

        refreshBtn.click(function (e) {
            var tempImg = K('<img src="' + imagePath.val() + '" />', document).css({
                position:'absolute',
                visibility:'hidden',
                top:0,
                left:'-1000px'
            });
            tempImg.bind('load', function () {
                setSize(tempImg.width(), tempImg.height());
                tempImg.remove();
            });
            K(document.body).append(tempImg);
        });

        widthBox.change(function (e) {
            if (originalWidth > 0) {
                heightBox.val(Math.round(originalHeight / originalWidth * parseInt(this.value, 10)));
            }
        });
        heightBox.change(function (e) {
            if (originalHeight > 0) {
                widthBox.val(Math.round(originalWidth / originalHeight * parseInt(this.value, 10)));
            }
        });

        /**
         * 设置大小
         * @param width
         * @param height
         */
        function setSize(width, height) {
            widthBox.val(width);
            heightBox.val(height);
            originalWidth = width;
            originalHeight = height;
        }

        if (options.imageUrl) {
            var imageId = "";
            var reg = new RegExp("/([\\-A-Za-z0-9.]*)\\.jpg$");
            if (reg.test(options.imageUrl)) {
                imageId = (RegExp.$1);
                imagePath.val($.ImageUtils.getSrcImageUrl(imageId));

                preView.empty();
                var _image = $('<img src="' + $.ImageUtils.getImageUrlFor150(imageId) + '" alt="预览图"/>').appendTo(preView);
            }
        }

        setSize(options.imageWidth, options.imageHeight);
        titleBox.val(options.imageTitle);
        alignBox.each(function () {
            if (this.value === options.imageAlign) {
                this.checked = true;
                return false;
            }
        });
    };

    self.plugin.image = {
        edit:function () {
            var img = self.plugin.getSelectedImage();
            self.plugin.imageDialog({
                imageUrl:img ? img.attr('data-ke-src') : '',
                imageWidth:img ? img.width() : '',
                imageHeight:img ? img.height() : '',
                imageTitle:img ? img.attr('title') : '',
                imageAlign:img ? img.attr('align') : ''
            });
        },
        'delete':function () {
            self.plugin.getSelectedImage().remove();
        }
    };
    self.clickToolbar(name, self.plugin.image.edit);
});