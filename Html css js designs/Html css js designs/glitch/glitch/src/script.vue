<!-- Use preprocessors via the lang attribute! e.g. <template lang="pug"> -->
<template>
    <div id="app">
        <svg class="glitch" :style="textStyle" :viewbox="viewBox">
            <text
                  :x="center"
                  y="50%"
                  text-anchor="middle"
                  dominant-baseline="middle"
                  fill="magenta"
            >
                {{ text }}
            </text>
            <text
                  :x="offRight"
                  y="50%"
                  text-anchor="middle"
                  dominant-baseline="middle"
                  fill="yellow"
            >
                {{ text }}
            </text>
            <text
                  :x="offLeft"
                  y="50%"
                  text-anchor="middle"
                  dominant-baseline="middle"
                  fill="cyan"
            >
                {{ text }}
            </text>
        </svg>
        <label>
            Text:
            <input type="text" v-model="text">
        </label>
        <label>
            Font Size:
            <input type="number" min="10" v-model="fontSize">
        </label>
        <label>
            Offset:
            <input type="number" min="1" v-model="offset">
        </label>
    </div>
</template>

<script>
export default {
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
</script>

<!-- Use preprocessors via the lang attribute! e.g. <style lang="scss"> -->
<style>
    html, body {
        margin: 0;
        padding: 0;
    }
    #app {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        height: 100vh;
        width: 100vw;
        font-family: Arial, sans-serif;
    }
    .glitch {
        width: 100%;
        font-family: Arial, sans-serif;
        font-weight: bold;
    }
    text {
        mix-blend-mode: multiply;
    }
</style>