(function ($) {
	// USE STRICT
	"use strict";
	var response = $.getJSON('api/analyze/' + localStorage["screenName"], function (userProfile, status) {
		if (status === "success") {
			$("#profileimage").attr("src", userProfile.imageURL);
			$("#name").text(userProfile.name);
			$("#screenName").text(userProfile.screenName);
			var date = new Date(Date(userProfile.joinedOn.toString));
			$("#joinedOn").text(date.toLocaleString());
			$("#location").text(userProfile.location);
			$("#language").text(userProfile.language);
			$("#bio").text(userProfile.bio);
			$("#tweetCount").text(userProfile.tweetCount);
			$("#ratio").text(userProfile.ratio);
			$("#statusFrequency").text(userProfile.statusFrequency);
			$("#tweetPerDay").text(userProfile.tweetPerDay);
			var ctx = $("#FFchart");
			if (ctx) {
				ctx.height = 280;
				var myChart = new Chart(ctx, {
					type: 'doughnut',
					data: {
						datasets: [{
							label: "followers vs following",
							data: [userProfile.followerCount, userProfile.followingCount],
							backgroundColor: [
								'#00b5e9',
								'#fa4251'
							],
							hoverBackgroundColor: [
								'#00b5e9',
								'#fa4251'
							],
							borderWidth: [
								0, 0
							],
							hoverBorderColor: [
								'transparent',
								'transparent'
							]
						}],
						labels: [
							'Followers',
							'Following'
						]
					},
					options: {
						maintainAspectRatio: false,
						responsive: true,
						cutoutPercentage: 55,
						animation: {
							animateScale: true,
							animateRotate: true
						},
						legend: {
							display: false
						}
					}
				});
			}
			var ctx = $("#TweetChart");
			if (ctx) {
				ctx.height = 280;
				var myChart = new Chart(ctx, {
					type: 'doughnut',
					data: {
						datasets: [{
							data: [userProfile.originalTweetCount, userProfile.retweetCount],
							backgroundColor: [
								'#00b5e9',
								'#fa4251'
							],
							hoverBackgroundColor: [
								'#00b5e9',
								'#fa4251'
							],
							borderWidth: [
								0, 0
							],
							hoverBorderColor: [
								'transparent',
								'transparent'
							]
						}],
						labels: [
							'Original Tweets',
							'Retweets'
						]
					},
					options: {
						maintainAspectRatio: false,
						responsive: true,
						cutoutPercentage: 55,
						animation: {
							animateScale: true,
							animateRotate: true
						},
						legend: {
							display: false
						}
					}
				});
			}
			var ctx = $("#timingChart");
			if (ctx) {
				ctx.height = 280;
				var myChart = new Chart(ctx, {
					type: 'bar',
					data: {
						labels: Object.keys(userProfile.tweetTiming),
						datasets: [{
							label: "Number of tweets",
							data: Object.values(userProfile.tweetTiming),
							borderColor: "rgba(0, 123, 255, 0.9)",
							borderWidth: "0",
							backgroundColor: "rgba(0, 123, 255, 0.5)"
						}]
					},
					options: {
						maintainAspectRatio: false,
						responsive: true,
						cutoutPercentage: 55,
						animation: {
							animateScale: true,
							animateRotate: true
						},
						legend: {
							position: 'top',
						},
						scales: {
							xAxes: [{
								ticks: {}
							}],
							yAxes: [{
								ticks: {
									beginAtZero: true,
								}
							}]
						}
					}
				});
			}
			$(Object.keys(userProfile.mentionsByCount)).each(function (index, data) {
				$("#mentionsBody").append('<tr> <td><a href="https://www.twitter.com/' + data + '">' + data + '</a></td><td>' + userProfile.mentionsByCount[data] + '</td></tr>')
			});
			$(Object.keys(userProfile.hashtagBycount)).each(function (index, data) {
				$("#hashtagsbody").append('<tr> <td><a href="https://www.twitter.com/hashtag/' + data + '">' + data + '</a></td><td>' + userProfile.hashtagBycount[data] + '</td></tr>')
			});
			$(Object.keys(userProfile.wordByFrequency)).each(function (index, data) {
				$("#wordusagebody").append('<tr> <td>' + data + '</td><td>' + userProfile.wordByFrequency[data] + '</td></tr>')
			});
		} else
			console.error(userProfile)
	});
	$(".loader").fadeOut(5000);
	(function ($) {
		// USE STRICT
		"use strict";
		$(".animsition").animsition({
			inClass: 'fade-in',
			outClass: 'fade-out',
			inDuration: 900,
			outDuration: 900,
			linkElement: 'a:not([target="_blank"]):not([href^="#"]):not([class^="chosen-single"])',
			loading: true,
			loadingParentElement: 'html',
			loadingClass: 'page-loader',
			loadingInner: '<div class="page-loader__spin"></div>',
			timeout: false,
			timeoutCountdown: 5000,
			onLoadEvent: true,
			browser: ['animation-duration', '-webkit-animation-duration'],
			overlay: false,
			overlayClass: 'animsition-overlay-slide',
			overlayParentElement: 'html',
		});
	})(jQuery);
})(jQuery);

function validate() {
	var screenName = document.getElementById('mscreenName').value;
	if (!screenName == "") {
		localStorage["screenName"] = screenName;
		return true;
	}
	return false;
}