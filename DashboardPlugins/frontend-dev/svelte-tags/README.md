# Svelte tag for Nagios component

This is the svelte tag for the "Nagios" component on the Goobi dashboard.

## How to build this thing

Install the dependencies...

```bash
npm install
```

...then bundle the js file

```bash
npm run build
```

...then copy the file to the correct folder

```
cp public/build/bundle.js ../../GUI/META-INF/resources/uii/plugin_dashboard_extended_svelte.js
```