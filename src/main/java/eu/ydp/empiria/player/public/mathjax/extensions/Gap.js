MathJax.Hub.Register.StartupHook("MathML Jax Ready", function () {
    var HTMLCSS = MathJax.OutputJax["HTML-CSS"];
    var MML = MathJax.ElementJax.mml;
    var SVG = MathJax.OutputJax.SVG;
    var getMathGap;

    function getGap(id) {
        if(typeof getMathGap === 'undefined'){
            getMathGap = document.getElementById("empiria.player").contentWindow.getMathGap;
        }

        return getMathGap(id);
    }

    MML.gap = MML.mbase.Subclass({
        type: "gap", isToken: true,
        texClass: MML.TEXCLASS.INNER,

        toHTML: function (parentElement) {
            var mathWrapper = this.HTMLcreateSpan(parentElement);
            this.HTMLhandleSize(mathWrapper);

            var id = this.attr['responseIdentifier'];
            var gap = getGap(id);
            mathWrapper.appendChild(gap);

            var HD = HTMLCSS.getHD(mathWrapper, true);
            mathWrapper.bbox = {h: HD.h, d: HD.d, w: true, exactW: false};

            this.HTMLhandleSpace(mathWrapper);
            this.HTMLhandleColor(mathWrapper);
            this.HTMLhandleDir(mathWrapper);

            HTMLCSS.Measured(mathWrapper, parentElement);

            return mathWrapper;
        },
        toSVG: function () {
            var FOREIGN = SVG.BBOX.Subclass({type: "foreignObject", removeable: false});
            var parentSvg = SVG.BBOX();
            this.SVGhandleSpace(parentSvg);

            var span = SVG.textSVG.parentNode;
            var id = this.attr['responseIdentifier'];
            var gap = getGap(id);

            span.insertBefore(gap, SVG.textSVG);
            var w = gap.offsetWidth, h = gap.offsetHeight;
            var strut = MathJax.HTML.addElement(gap, "span", {
                style: {
                    display: "inline-block", overflow: "hidden", height: h + "px",
                    width: "1px", marginRight: "-1px"
                }
            });
            var d = gap.offsetHeight - h;
            if (h === d) {
                h /= 2;
            } else {
                h -= d;
            }
            gap.removeChild(strut);
            span.removeChild(gap);

            var scale = 1000 / SVG.em;
            var svg = FOREIGN({
                y: (-h) + "px", width: w + "px", height: (h + d) + "px",
                transform: "scale(" + scale + ") matrix(1 0 0 -1 0 0)"
            });

            svg.element.appendChild(gap);

            svg.w = w * scale;
            svg.h = h * scale;
            svg.d = d * scale;
            svg.r = svg.w;
            svg.l = 0;
            svg.Clean();
            this.SVGhandleColor(svg);
            this.SVGsaveData(svg);

            parentSvg.Add(svg, parentSvg.w, 0, true);
            parentSvg.Clean();
            this.SVGhandleColor(parentSvg);
            this.SVGsaveData(parentSvg);
            return parentSvg;
        }
    });
});

MathJax.Ajax.loadComplete("[MathJax]/extensions/Gap.js");