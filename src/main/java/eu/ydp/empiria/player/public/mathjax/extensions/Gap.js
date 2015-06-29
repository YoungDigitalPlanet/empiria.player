MathJax.Hub.Register.StartupHook("MathML Jax Ready", function () {
    var HTMLCSS = MathJax.OutputJax["HTML-CSS"];
    var MML = MathJax.ElementJax.mml;

    MML.gap = MML.mbase.Subclass({
        type: "gap", isToken: true,
        texClass: MML.TEXCLASS.INNER,

        toHTML: function (parentElement) {
            var mathWrapper = this.HTMLcreateSpan(parentElement);
            this.HTMLhandleSize(mathWrapper);

            var id = this.attr['responseIdentifier'];
            var gap = MathJax.Hub.getMathGap(id);
            mathWrapper.appendChild(gap);

            var HD = HTMLCSS.getHD(mathWrapper, true);
            mathWrapper.bbox = {h: HD.h, d: HD.d, w: true, exactW: false};

            this.HTMLhandleSpace(mathWrapper);
            this.HTMLhandleColor(mathWrapper);
            this.HTMLhandleDir(mathWrapper);

            HTMLCSS.Measured(mathWrapper, parentElement);

            return mathWrapper;
        }
    });
});

MathJax.Ajax.loadComplete("[MathJax]/extensions/Gap.js");