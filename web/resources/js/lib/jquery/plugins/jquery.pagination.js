/**
 * This jQuery plugin displays pagination links inside the selected elements.
 *
 * @author Gabriel Birke (birke *at* d-scribe *dot* de)
 * @version 1.1
 * @param {int} maxentries Number of entries to paginate
 * @param {Object} opts Several options (see README for documentation)
 * @return {Object} jQuery Object
 */
jQuery.fn.pagination = function(maxentries, opts) {
    opts = jQuery.extend({
        items_per_page:10,
        num_display_entries:0,
        current_page:0,
        num_edge_entries:0,
        link_to:"#",
        prev_text:"上一页",
        next_text:"下一页",
        ellipse_text:"...",
        prev_show_always:true,
        next_show_always:true,
        first_show_always:false,
        first_show_class:"first",
        last_show_class:"last",
        first_show:"<<",
        last_show_always:false,
        last_show:">>",
        link_to_pageNo:true,
        other_need_current:false,
        callback:function() {
            return false;
        }
    }, opts || {});

    return this.each(function() {
        /**
         * Calculate the maximum number of pages
         */
        function numPages() {
            return Math.ceil(maxentries / opts.items_per_page);
        }

        /**
         * Calculate start and end point of pagination links depending on
         * current_page and num_display_entries.
         * @return {Array}
         */
        function getInterval() {
            if (opts.num_display_entries > 0) {
                opts.num_display_entries = 3;
            }
            if (opts.num_display_entries == 0) {
                opts.link_to_pageNo = false;
            }
            var ne_half = Math.ceil(opts.num_display_entries / 2);
            var np = numPages();
            var upper_limit = np - opts.num_display_entries;
            var start = current_page >= ne_half ? Math.max(Math.min(current_page - (opts.num_display_entries == 0 ? 0 : 1), upper_limit), 0) : 0;
            var end = current_page >= ne_half ? Math.min(current_page + ne_half, np) : Math.min(opts.num_display_entries, np);
            return [start,end];
        }

        /**
         * This is the event handling function for the pagination links.
         * @param {int} page_id The new page number
         */
        function pageSelected(page_id, evt) {
            current_page = page_id;
            drawLinks();
            var continuePropagation = opts.callback(page_id, panel);
            if (!continuePropagation) {
                if (evt.stopPropagation) {
                    evt.stopPropagation();
                }
                else {
                    evt.cancelBubble = true;
                }
            }
            return continuePropagation;
        }

        /**
         * This function inserts the pagination links into the container element
         */
        function drawLinks() {
            if (opts.num_edge_entries > 1) {
                opts.num_edge_entries = 1;
            }
            panel.empty();
            var interval = getInterval();
            var np = numPages();
            // This helper function returns a handler function that calls pageSelected with the right page_id
            var getClickHandler = function(page_id) {
                return function(evt) {
                    return pageSelected(page_id, evt);
                }
            }
            // Helper function for generating a single link (or a span tag if it'S the current page)
            var appendItem = function(page_id, appendopts) {
                page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // Normalize page id to sane value
                appendopts = jQuery.extend({text:page_id + 1, classes:""}, appendopts || {});
                if (page_id == current_page) {
                    var lnk = $("<span class='current'>" + (appendopts.text) + "</span>");
                    if (opts.other_need_current != null && opts.other_need_current == false) {
                        if (appendopts.classes == opts.first_show_class || appendopts.classes == "prev" || appendopts.classes == "next" || appendopts.classes == opts.last_show_class) {
                            lnk = $("<span>" + (appendopts.text) + "</span>");
                        }
                    }
                }
                else
                {
                    var lnk = $("<a>" + (appendopts.text) + "</a>")
                            .bind("click", getClickHandler(page_id))
                            .attr('href', opts.link_to.replace(/__id__/, page_id));
                }
                if (appendopts.classes) {
                    lnk.addClass(appendopts.classes);
                }
                panel.append(lnk);
            }
            // Generate "Previous"-Link
            if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
                var firstClass = opts.first_show_class ? opts.first_show_class : "prev";
                //第一页
                if (opts.first_show_always) {
                    if (opts.first_show) {
                        appendItem(0, {text:opts.first_show,classes:firstClass});
                    }
                    else {
                        appendItem(0, {text:"首页",classes:firstClass});
                    }
                }
                appendItem(current_page - 1, {text:opts.prev_text, classes:"prev"});
            }
            // Generate starting points
            if (interval[0] > 0 && opts.num_edge_entries > 0)
            {
                var end = Math.min(opts.num_edge_entries, interval[0]);
                for (var i = 0; i < end; i++) {
                    appendItem(i);
                }
                if (opts.num_edge_entries < interval[0] && opts.ellipse_text)
                {
                    jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                }
            }
            // Generate interval links
            for (var i = interval[0]; i < interval[1]; i++) {
                appendItem(i);
            }
            // Generate ending points
            if (interval[1] < np && opts.num_edge_entries > 0)
            {
                if (np - opts.num_edge_entries > interval[1] && opts.ellipse_text)
                {
                    jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                }
                var begin = Math.max(np - opts.num_edge_entries, interval[1]);
                for (var i = begin; i < np; i++) {
                    appendItem(i);
                }

            }
            // Generate "Next"-Link
            if (opts.next_text && (current_page < np - 1 || opts.next_show_always)) {
                appendItem(current_page + 1, {text:opts.next_text, classes:"next"});
                /*最后一页*/
                var lastClass = opts.last_show_class ? opts.last_show_class : "next";
                if (opts.last_show_always) {
                    if (opts.last_show) {
                        appendItem(np - 1, {text:opts.last_show, classes:lastClass});
                    }
                    else {
                        appendItem(np - 1, {text:"末页", classes:lastClass});
                    }
                }
            }
            if (opts.link_to_pageNo) {
                var lnk = $("<input type='text' class='txtbox' style='width:20px'>").keypress(function() {
                    if ((event.keyCode > 47) && (event.keyCode < 58)) {
                        $("[name='page_go_button']").removeAttr("disabled");
                        return true;
                    } else if (event.keyCode == 13) {
                        if ($.trim($(this).val()) == "" || $.trim($(this).val()) > np || $.trim($(this).val()) == "0" || parseInt($.trim($(this).val())) - 1 == current_page) {
                            return false;
                        }
                        current_page = parseInt($.trim($(this).val()).replace(/^0*/, "")) - 1;
                        drawLinks();
                        var continuePropagation = opts.callback(current_page, panel);
                        return continuePropagation;
                    } else {
                        return false;
                    }
                });

                var button = $("<input type='button' class='button' name='page_go_button' value='Go' disabled='disabled'>").click(function() {
                    if ($.trim($(lnk).val()) == "" || $.trim($(lnk).val()) > np || $.trim($(lnk).val()) == "0" || parseInt($.trim($(lnk).val())) - 1 == current_page) {
                        return false;
                    }
                    current_page = parseInt($.trim($(lnk).val()).replace(/^0*/, "")) - 1;
                    drawLinks();
                    var continuePropagation = opts.callback(current_page, panel);
                    return continuePropagation;
                });
                panel.append(lnk);
                panel.append(button);
                $(lnk).before("&nbsp;跳到&nbsp;");
                $(lnk).after("页");
            }
        }

        // Extract current_page from options
        var current_page = opts.current_page;
        // Create a sane value for maxentries and items_per_page
        maxentries = (!maxentries || maxentries < 0) ? 1 : maxentries;
        opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0) ? 1 : opts.items_per_page;
        // Store DOM element for easy access from all inner functions
        var panel = jQuery(this);
        // Attach control functions to the DOM element
        this.getCurrentPageIndex = function() {
            return parseInt(parseInt(current_page, 10) + 1);
        }
        this.selectPage = function(page_id) {
            pageSelected(page_id);
        }
        this.prevPage = function() {
            if (current_page > 0) {
                pageSelected(current_page - 1);
                return true;
            }
            else {
                return false;
            }
        }
        this.nextPage = function() {
            if (current_page < numPages() - 1) {
                pageSelected(current_page + 1);
                return true;
            }
            else {
                return false;
            }
        }
        // When all initialisation is done, draw the links
        drawLinks();
    });
}


