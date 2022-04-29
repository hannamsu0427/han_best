/*!
 * jQuery Tickerme Plugin v1.0
 */

(function($){

	$.fn.tickerme = function(options) {

		var opts = $.extend( {}, $.fn.tickerme.defaults, options );

		return this.each(function(){

			var ticker = $(this);
			// SVG definitions for the play/pause/previous/next controls:
			var control_styles = '<style type="text/css">';
			control_styles += '#ticker .subject{display:none; text-align:center; line-height:53px}';
			control_styles += '</style>';
			// Array to contain news contents:
			var contents = [];
			var position = -1;
			var timer;

			init();

			/* Initialise */

			function init() {

				// Hide all:
				$(ticker).hide();

				// Create the buttons:
				$('body').prepend(control_styles);

				var controls = '<ul class="ticker_list">';
				controls += '<li class="subject"><div id="news"></div></li>';
				controls += '</ul>';
				$(controls).insertAfter(ticker);

				// Load up the array:
				$(ticker).children().each(function(i){
					contents[i] = ($(this).html());
				});

				load_container();
			}

			/* load_container */

			function load_container() {

				if (position == (contents.length - 1)) {
					position = 0;
				} else {
					position++;
				}		
				// Fade out the current item, replace it with the next one, and fade it in:
				if (opts.type == 'fade') {
					$('#news').fadeOut(opts.fade_speed,function(){
						$('.ticker_list .subject').html('<div id="news">'+contents[position]+'</div>');
						$('#news').fadeIn(opts.fade_speed);
					});
				}
				timer = setTimeout(load_container,opts.duration);
			}

			/* Control functions */

			$('.bt_prev').click(function(){
				if (position == 0) {
					position = (contents.length - 1);
				} else {
					position--;
				}
				$('.subject').html('<div id="news" style="display:block">'+contents[position]+'</div>');
				if (opts.auto_stop) clearTimeout(timer);;
				return false;
			});

			$('.bt_next').click(function(){
				if (position == (contents.length - 1)) {
					position = 0;
				} else {
					position++;
				}
				$('.subject').html('<div id="news" style="display:block">'+contents[position]+'</div>');
				if (opts.auto_stop) clearTimeout(timer);;
				return false;
			});
			
			$(document).on("mouseenter", ".ticker_list", function() {
				if (opts.auto_stop) clearTimeout(timer);
				return false;
			});
			
			$(document).on("mouseleave", ".ticker_list", function() {
				load_container();
				return false;
			});

		});

	};

	$.fn.tickerme.defaults = {
		fade_speed: 500,
		duration: 5000,
		auto_stop: true,
		type: 'fade',
	};

}(jQuery));