var font = (function () {
    var navU = navigator.userAgent;

    // Android Mobile
    var isAndroidMobile = navU.indexOf('Android') > -1 && navU.indexOf('Mozilla/5.0') > -1 && navU.indexOf('AppleWebKit') > -1;

    // Apple webkit
    var regExAppleWebKit = new RegExp(/AppleWebKit\/([\d.]+)/);
    var resultAppleWebKitRegEx = regExAppleWebKit.exec(navU);
    var appleWebKitVersion = (resultAppleWebKitRegEx === null ? null : parseFloat(regExAppleWebKit.exec(navU)[1]));

    // Chrome
    var regExChrome = new RegExp(/Chrome\/([\d.]+)/);
    var resultChromeRegEx = regExChrome.exec(navU);
    var chromeVersion = (resultChromeRegEx === null ? null : parseFloat(regExChrome.exec(navU)[1]));

    // Native Android Browser
    var isAndroidStackBrowser = isAndroidMobile && (appleWebKitVersion !== null && appleWebKitVersion < 537) || (chromeVersion !== null && chromeVersion < 37);
    return isAndroidStackBrowser ? "STIX" : "TeX";
})();

MathJax.Hub.yElements = [];

MathJax.Hub.yProcessElements = function () {
    var elements = MathJax.Hub.yElements;
    if (elements.length === 0) {
        return;
    }
    if (elements.length === 1) {
        elements = elements[0];
    }

    MathJax.Hub.Queue(["Process", MathJax.Hub, elements]);
    MathJax.Hub.yElements = [];

};

MathJax.Hub.Config({
    config: ["MMLorHTML.js"],
    extensions: ["Gap.js"],
    jax: ["input/MathML", "output/HTML-CSS", "output/NativeMML"],
    showMathMenu: false,
    showMathMenuMSIE: false,
    showProcessingMessages: false,
    messageStyle: "none",
    "HTML-CSS": {
        availableFonts: [font],
        preferredFont: font,
        webFont: font,
        imageFont: null,
        matchFontHeight: true
    }
});

MathJax.Ajax.loadComplete("[MathJax]/config/yJax.js");
