MathJax.Hub.Register.StartupHook("MathML Jax Ready", function () {
    var HTMLCSS = MathJax.OutputJax["HTML-CSS"];
    var MML = MathJax.ElementJax.mml;

    MML.gap = MML.mbase.Subclass({
        type: "gap", isToken: true,
        texClass: MML.TEXCLASS.INNER,

        toHTML: function (span) {
            var newDiv = this.HTMLcreateSpan(span);
            this.HTMLhandleSize(newDiv);

            var id = this.attr['responseIdentifier'];
            var gap = window.getMathGap(id);
            newDiv.appendChild(gap);

            var HD = HTMLCSS.getHD(newDiv, true);
            newDiv.bbox = {h: HD.h, d: HD.d, w: true, exactW: false};

            this.HTMLhandleSpace(newDiv);
            this.HTMLhandleColor(newDiv);
            this.HTMLhandleDir(newDiv);

            HTMLCSS.Measured(newDiv, span);

            return newDiv;
        }
    });
});

MathJax.Ajax.loadComplete("[MathJax]/extensions/Gap.js");