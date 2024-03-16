var script = {
    data() {
        return {
            text: 'GLITCH',
            fontSize: 100,
            offset: 3,
            screenWidth: document.body.clientWidth
        };
    },
    computed: {
        textStyle() {
            return {
                'font-size': `${this.fontSize}px`
            }
        },
        viewBox() {
            return `0 0 ${this.screenWidth} ${this.fontSize}`;
        },
        center() {
            return this.screenWidth / 2;
        },
        offLeft() {
            return this.center - this.offset;
        },
        offRight() {
            return this.center + Number(this.offset);
        }
    }
};

function normalizeComponent(template, style, script, scopeId, isFunctionalTemplate, moduleIdentifier /* server only */, shadowMode, createInjector, createInjectorSSR, createInjectorShadow) {
    if (typeof shadowMode !== 'boolean') {
        createInjectorSSR = createInjector;
        createInjector = shadowMode;
        shadowMode = false;
    }
    // Vue.extend constructor export interop.
    const options = typeof script === 'function' ? script.options : script;
    // render functions
    if (template && template.render) {
        options.render = template.render;
        options.staticRenderFns = template.staticRenderFns;
        options._compiled = true;
        // functional template
        if (isFunctionalTemplate) {
            options.functional = true;
        }
    }
    // scopedId
    if (scopeId) {
        options._scopeId = scopeId;
    }
    let hook;
    if (moduleIdentifier) {
        // server build
        hook = function (context) {
            // 2.3 injection
            context =
                context || // cached call
                    (this.$vnode && this.$vnode.ssrContext) || // stateful
                    (this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext); // functional
            // 2.2 with runInNewContext: true
            if (!context && typeof __VUE_SSR_CONTEXT__ !== 'undefined') {
                context = __VUE_SSR_CONTEXT__;
            }
            // inject component styles
            if (style) {
                style.call(this, createInjectorSSR(context));
            }
            // register component module identifier for async chunk inference
            if (context && context._registeredComponents) {
                context._registeredComponents.add(moduleIdentifier);
            }
        };
        // used by ssr in case component is cached and beforeCreate
        // never gets called
        options._ssrRegister = hook;
    }
    else if (style) {
        hook = shadowMode
            ? function (context) {
                style.call(this, createInjectorShadow(context, this.$root.$options.shadowRoot));
            }
            : function (context) {
                style.call(this, createInjector(context));
            };
    }
    if (hook) {
        if (options.functional) {
            // register for functional component in vue file
            const originalRender = options.render;
            options.render = function renderWithStyleInjection(h, context) {
                hook.call(context);
                return originalRender(h, context);
            };
        }
        else {
            // inject component registration as beforeCreate hook
            const existing = options.beforeCreate;
            options.beforeCreate = existing ? [].concat(existing, hook) : [hook];
        }
    }
    return script;
}

const isOldIE = typeof navigator !== 'undefined' &&
    /msie [6-9]\\b/.test(navigator.userAgent.toLowerCase());
function createInjector(context) {
    return (id, style) => addStyle(id, style);
}
let HEAD;
const styles = {};
function addStyle(id, css) {
    const group = isOldIE ? css.media || 'default' : id;
    const style = styles[group] || (styles[group] = { ids: new Set(), styles: [] });
    if (!style.ids.has(id)) {
        style.ids.add(id);
        let code = css.source;
        if (css.map) {
            // https://developer.chrome.com/devtools/docs/javascript-debugging
            // this makes source maps inside style tags work properly in Chrome
            code += '\n/*# sourceURL=' + css.map.sources[0] + ' */';
            // http://stackoverflow.com/a/26603875
            code +=
                '\n/*# sourceMappingURL=data:application/json;base64,' +
                    btoa(unescape(encodeURIComponent(JSON.stringify(css.map)))) +
                    ' */';
        }
        if (!style.element) {
            style.element = document.createElement('style');
            style.element.type = 'text/css';
            if (css.media)
                style.element.setAttribute('media', css.media);
            if (HEAD === undefined) {
                HEAD = document.head || document.getElementsByTagName('head')[0];
            }
            HEAD.appendChild(style.element);
        }
        if ('styleSheet' in style.element) {
            style.styles.push(code);
            style.element.styleSheet.cssText = style.styles
                .filter(Boolean)
                .join('\n');
        }
        else {
            const index = style.ids.size - 1;
            const textNode = document.createTextNode(code);
            const nodes = style.element.childNodes;
            if (nodes[index])
                style.element.removeChild(nodes[index]);
            if (nodes.length)
                style.element.insertBefore(textNode, nodes[index]);
            else
                style.element.appendChild(textNode);
        }
    }
}

/* script */
const __vue_script__ = script;

/* template */
var __vue_render__ = function() {
  var _vm = this;
  var _h = _vm.$createElement;
  var _c = _vm._self._c || _h;
  return _c("div", { attrs: { id: "app" } }, [
    _c(
      "svg",
      {
        staticClass: "glitch",
        style: _vm.textStyle,
        attrs: { viewbox: _vm.viewBox }
      },
      [
        _c(
          "text",
          {
            attrs: {
              x: _vm.center,
              y: "50%",
              "text-anchor": "middle",
              "dominant-baseline": "middle",
              fill: "magenta"
            }
          },
          [_vm._v("\n            " + _vm._s(_vm.text) + "\n        ")]
        ),
        _vm._v(" "),
        _c(
          "text",
          {
            attrs: {
              x: _vm.offRight,
              y: "50%",
              "text-anchor": "middle",
              "dominant-baseline": "middle",
              fill: "yellow"
            }
          },
          [_vm._v("\n            " + _vm._s(_vm.text) + "\n        ")]
        ),
        _vm._v(" "),
        _c(
          "text",
          {
            attrs: {
              x: _vm.offLeft,
              y: "50%",
              "text-anchor": "middle",
              "dominant-baseline": "middle",
              fill: "cyan"
            }
          },
          [_vm._v("\n            " + _vm._s(_vm.text) + "\n        ")]
        )
      ]
    ),
    _vm._v(" "),
    _c("label", [
      _vm._v("\n        Text:\n        "),
      _c("input", {
        directives: [
          {
            name: "model",
            rawName: "v-model",
            value: _vm.text,
            expression: "text"
          }
        ],
        attrs: { type: "text" },
        domProps: { value: _vm.text },
        on: {
          input: function($event) {
            if ($event.target.composing) {
              return
            }
            _vm.text = $event.target.value;
          }
        }
      })
    ]),
    _vm._v(" "),
    _c("label", [
      _vm._v("\n        Font Size:\n        "),
      _c("input", {
        directives: [
          {
            name: "model",
            rawName: "v-model",
            value: _vm.fontSize,
            expression: "fontSize"
          }
        ],
        attrs: { type: "number", min: "10" },
        domProps: { value: _vm.fontSize },
        on: {
          input: function($event) {
            if ($event.target.composing) {
              return
            }
            _vm.fontSize = $event.target.value;
          }
        }
      })
    ]),
    _vm._v(" "),
    _c("label", [
      _vm._v("\n        Offset:\n        "),
      _c("input", {
        directives: [
          {
            name: "model",
            rawName: "v-model",
            value: _vm.offset,
            expression: "offset"
          }
        ],
        attrs: { type: "number", min: "1" },
        domProps: { value: _vm.offset },
        on: {
          input: function($event) {
            if ($event.target.composing) {
              return
            }
            _vm.offset = $event.target.value;
          }
        }
      })
    ])
  ])
};
var __vue_staticRenderFns__ = [];
__vue_render__._withStripped = true;

  /* style */
  const __vue_inject_styles__ = function (inject) {
    if (!inject) return
    inject("data-v-31ad0c05_0", { source: "\nhtml, body {\n    margin: 0;\n    padding: 0;\n}\n#app {\n    display: flex;\n    flex-direction: column;\n    justify-content: center;\n    align-items: center;\n    height: 100vh;\n    width: 100vw;\n    font-family: Arial, sans-serif;\n}\n.glitch {\n    width: 100%;\n    font-family: Arial, sans-serif;\n    font-weight: bold;\n}\ntext {\n    mix-blend-mode: multiply;\n}\n", map: {"version":3,"sources":["/tmp/codepen/vuejs/src/pen.vue"],"names":[],"mappings":";AAuGA;IACA,SAAA;IACA,UAAA;AACA;AACA;IACA,aAAA;IACA,sBAAA;IACA,uBAAA;IACA,mBAAA;IACA,aAAA;IACA,YAAA;IACA,8BAAA;AACA;AACA;IACA,WAAA;IACA,8BAAA;IACA,iBAAA;AACA;AACA;IACA,wBAAA;AACA","file":"pen.vue","sourcesContent":["<!-- Use preprocessors via the lang attribute! e.g. <template lang=\"pug\"> -->\n<template>\n    <div id=\"app\">\n        <svg class=\"glitch\" :style=\"textStyle\" :viewbox=\"viewBox\">\n            <text\n                  :x=\"center\"\n                  y=\"50%\"\n                  text-anchor=\"middle\"\n                  dominant-baseline=\"middle\"\n                  fill=\"magenta\"\n            >\n                {{ text }}\n            </text>\n            <text\n                  :x=\"offRight\"\n                  y=\"50%\"\n                  text-anchor=\"middle\"\n                  dominant-baseline=\"middle\"\n                  fill=\"yellow\"\n            >\n                {{ text }}\n            </text>\n            <text\n                  :x=\"offLeft\"\n                  y=\"50%\"\n                  text-anchor=\"middle\"\n                  dominant-baseline=\"middle\"\n                  fill=\"cyan\"\n            >\n                {{ text }}\n            </text>\n        </svg>\n        <label>\n            Text:\n            <input type=\"text\" v-model=\"text\">\n        </label>\n        <label>\n            Font Size:\n            <input type=\"number\" min=\"10\" v-model=\"fontSize\">\n        </label>\n        <label>\n            Offset:\n            <input type=\"number\" min=\"1\" v-model=\"offset\">\n        </label>\n    </div>\n</template>\n\n<script>\nexport default {\n    data() {\n        return {\n            text: 'GLITCH',\n            fontSize: 100,\n            offset: 3,\n            screenWidth: document.body.clientWidth\n        };\n    },\n    computed: {\n        textStyle() {\n            return {\n                'font-size': `${this.fontSize}px`\n            }\n        },\n        viewBox() {\n            return `0 0 ${this.screenWidth} ${this.fontSize}`;\n        },\n        center() {\n            return this.screenWidth / 2;\n        },\n        offLeft() {\n            return this.center - this.offset;\n        },\n        offRight() {\n            return this.center + Number(this.offset);\n        }\n    }\n};\n</script>\n\n<!-- Use preprocessors via the lang attribute! e.g. <style lang=\"scss\"> -->\n<style>\n    html, body {\n        margin: 0;\n        padding: 0;\n    }\n    #app {\n        display: flex;\n        flex-direction: column;\n        justify-content: center;\n        align-items: center;\n        height: 100vh;\n        width: 100vw;\n        font-family: Arial, sans-serif;\n    }\n    .glitch {\n        width: 100%;\n        font-family: Arial, sans-serif;\n        font-weight: bold;\n    }\n    text {\n        mix-blend-mode: multiply;\n    }\n</style>"]}, media: undefined });

  };
  /* scoped */
  const __vue_scope_id__ = undefined;
  /* module identifier */
  const __vue_module_identifier__ = undefined;
  /* functional template */
  const __vue_is_functional_template__ = false;
  /* style inject SSR */
  
  /* style inject shadow dom */
  

  
  const __vue_component__ = /*#__PURE__*/normalizeComponent(
    { render: __vue_render__, staticRenderFns: __vue_staticRenderFns__ },
    __vue_inject_styles__,
    __vue_script__,
    __vue_scope_id__,
    __vue_is_functional_template__,
    __vue_module_identifier__,
    false,
    createInjector,
    undefined,
    undefined
  );

export default __vue_component__;