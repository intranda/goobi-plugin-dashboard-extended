import svelte from 'rollup-plugin-svelte';
import resolve from '@rollup/plugin-node-resolve';
import livereload from 'rollup-plugin-livereload';
import terser from '@rollup/plugin-terser';
import css from 'rollup-plugin-css-only';

const production = !process.env.ROLLUP_WATCH;

export default {
	input: 'svelte-tags/src/main.js',
	output: {
		sourcemap: true,
		format: 'iife',
		name: 'app',
		file: 'resources/dist/intranda_dashboard_extended/js/plugin_dashboard_extended_svelte.js'
	},
	plugins: [
		svelte({
			compilerOptions: {
				// enable run-time checks when not in production
				dev: !production
			},
			emitCss: false
		}),
		css({ output: false }),
		resolve({
			browser: true,
			dedupe: ['svelte']
		}),

		// Watch the output directory and refresh the
		// browser on changes when not in production
		!production && livereload('resources/dist'),

		// If we're building for production (npm run build
		// instead of npm run dev), minify
		production && terser()
	],
	watch: {
		clearScreen: false
	}
};