(function ($) {
	// USE STRICT
	"use strict";
	var response = $.getJSON('api/analyze/' + localStorage["screenName"], function (userProfile, status) {
		if (status === "success") {
			$("#profileimage").attr("src", userProfile.imageURL);
			$("#name").text(userProfile.name);
			$("#screenName").text(userProfile.screenName);
			$("#screenName").attr("href", "https://twitter.com/" + userProfile.screenName);
			$("#joinedOn").text(userProfile.joinedOn.toString());
			$("#location").text(userProfile.location);
			$("#bio").text(userProfile.bio);
			$("#followers").text("Followers:"+" "+userProfile.followerCount);
            $("#following").text("Following:"+" "+userProfile.followingCount);
           	$("#OT").text("Orignial Tweets:"+" "+userProfile.originalTweetCount);
            $("#RT").text("Retweets:"+" "+userProfile.retweetCount);
			$("#tweetCount").text(userProfile.tweetCount);
			$("#ratio").text(userProfile.ratio);
			$("#statusFrequency").text(userProfile.statusFrequency);
			$("#tweetPerDay").text(userProfile.tweetPerDay);

			var ctx = $("#FFchart");
			if (ctx) {
				ctx.height = 280;
				var myChart = new Chart(ctx, {
					type: 'pie',
					data: {
						datasets: [{
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
						animation: {
							animateScale: true,
							animateRotate: true
						},
						pieceLabel: {
							render: 'percentage',
							fontColor: '#FFFFFF'
						},
						legend: {
							display: false
						}
					}
				});
			}
			if (userProfile.followerCount == 0 && userProfile.followingCount == 0) {
				$("#ffdiv").remove();
			}


			if (userProfile.tweetCount != 0) {
				var ctx = $("#TweetChart");
				if (ctx) {
					ctx.height = 280;
					var myChart = new Chart(ctx, {
						type: 'pie',
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
							animation: {
								animateScale: true,
								animateRotate: true
							},
							pieceLabel: {
								render: 'percentage',
								fontColor: '#FFFFFF'
							},
							legend: {
								display: false
							}
						}
					});
				}
			} else {
				$("#tweetsdiv").remove();
			}
			if (Object.keys(userProfile.tweetTiming).length != 0) {
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
									scaleLabel: {
										display: true,
										labelString: 'Hours(in 24 hours format)'
									}
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
			} else {
				$("#timingsection").remove();
			}
			if (Object.keys(userProfile.mentionsByCount).length != 0) {
				$(Object.keys(userProfile.mentionsByCount)).each(function (index, data) {
					$("#mentionsBody").append('<tr> <td><a target="_blank" href="https://www.twitter.com/' + data + '">' + data + '</a></td><td>' + userProfile.mentionsByCount[data] + '</td></tr>')
				});
			} else {
				$("#mentionsdiv").remove();
			}
			if (Object.keys(userProfile.hashtagBycount).length != 0) {
				$(Object.keys(userProfile.hashtagBycount)).each(function (index, data) {
					$("#hashtagsbody").append('<tr> <td><a target="_blank" href="https://www.twitter.com/hashtag/' + data + '">' + data + '</a></td><td>' + userProfile.hashtagBycount[data] + '</td></tr>')
				});
			} else {
				$("#hashtagdiv").remove();
			}
			if (Object.keys(userProfile.wordByFrequency).length != 0) {
				$(Object.keys(userProfile.wordByFrequency)).each(function (index, data) {
					$("#wordusagebody").append('<tr> <td>' + data + '</td><td>' + userProfile.wordByFrequency[data] + '</td></tr>')
				});
			} else {
				$("#wordusagediv").remove();
			}
		}
	}).fail(function (err) {
		console.error(err.responseText);
		alert(err.responseText);
		window.location.href = "/index.html"
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