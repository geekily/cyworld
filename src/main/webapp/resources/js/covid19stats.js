//<![CDATA[
(function ( $ ) {
	
	var mode;
	var cases = 0;
	var deaths = 0;
	var recovered = 0;
	var country = "";
 
    $.covid19stats = function( options ) {
	    	    
	    // Manage default value if options are not set...
		var defaults = {
		element: "#covid-widget", // Target element ID
		  countryCode: "KR", // ISO Country Code (get the list here : https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
		  showImg: true, // True or false to show the left covid image
		  showCases: true, // True or false to show the "cases" stat
		  showDeaths: true, // True or false to show the "deaths" stat
		  showRecovered: true, // True or false to show the "recovered" stat
		  showRightLabel: true // True or false to show the "right label"
		}
		
		var settings = $.extend({}, defaults, options);
		
		// Check if the target element exists 
		if(settings.element == "" || !$(settings.element).length) {
		 
			console.log("[covid19stats debug] : the target element does not exist or has not been specified!");
			return;
		 
		}
		
		if(settings.countryCode == "") {
			
			mode = "world";
			var api_url = "https://coronavirus-tracker-api.herokuapp.com/v2/latest";
			
		} else {
			
			mode = "country";
			var api_url = "https://coronavirus-tracker-api.herokuapp.com/v2/locations?country_code=" + settings.countryCode;
			
		}
				
		// Get the stats from the API
		fetch(api_url)
		.then(response => response.json())
		.then(data => {
			
			if(mode == "country") {
				
				var location = data.locations[0];
				console.log(location);
				var latest = location.latest;
				cases = latest.confirmed;
				deaths = latest.deaths;
				recovered = latest.recovered;
				country = location.country;
				
			} else {
				
				var latest = data.latest;
				cases = latest.confirmed;
				deaths = latest.deaths;
				recovered = latest.recovered;
				country = "Worldwide";
				
			}
			
		})
		.then(() => {
			
			// We have the results, we can build the DOM
			var dom_element = '';
			dom_element += '<div class="covid19container">';
			
				if(settings.showImg) {
					dom_element += '<div class="covid__hero_container">';
						dom_element += '<div class="covid__hero"><img src="../resources/images/covid_green.png" width="150" height="150" alt="" class="covid__img"></div>';
					dom_element += '</div>';
				}
				
				dom_element += '<div class="covid__content">';
					dom_element += '<div class="covid__stats">';
					
						if(settings.showCases) {
							dom_element += '<div class="covid__stat cases">' + formatNumber(cases) + '<span>Cases</span></div>';
						}
						
						if(settings.showDeaths) {
							dom_element += '<div class="covid__stat deaths">' + formatNumber(deaths) + '<span>Deaths</span></div>';
						}
						
						if(settings.showRecovered) {
							dom_element += '<div class="covid__stat recovered">' + formatNumber(recovered) + '<span>Recovered</span></div>';
						}
						
					dom_element += '</div>';
					dom_element += '<div class="covid__location">' + country + ' Stats</div>';
				dom_element += '</div>';
				
				if(settings.showRightLabel) {
					dom_element += '<div class="covid__status">';
						dom_element += 'Covid19 Stats';
					dom_element += '</div>';
				}
				
			dom_element += '</div>';
			
			$(settings.element).hide().html(dom_element).fadeIn();
			
		})
		.catch((error) => {
			console.log("[covid19stats debug] : there was a problem retrieving data from API. Please check that the country you set is a supported country.");
		});
		
			    
    };
    
    /* COMMON FUNCTIONS */
	function formatNumber(number)
	{
		number = number.toFixed(0) + '';
		x = number.split('.');
		x1 = x[0];
		x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		return x1 + x2;
	}
	
}( jQuery )); 
//]]>
