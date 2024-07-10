const path = require("path")
const webpack = require("webpack")

module.exports = {
    entry: './riotjs/main.js',
    mode: "development",
    output: {
        path: path.resolve(__dirname, 'resources/dist/intranda_dashboard_extended/js/'),
        filename: 'plugin_dashboard_extended_tags.js'
    },
    module: {
      rules: [
        {
          test: /\.tag$/,
          exclude: /node_modules/,
          use: [{
            loader: '@riotjs/webpack-loader',
            options: {
              hot: false, // set it to true if you are using hmr
              // add here all the other @riotjs/compiler options riot.js.org/compiler
              // template: 'pug' for example
            }
          }]
        },
        {
          test: /\.js$/,
          exclude: /node_modules/,
          use: {
            loader: 'babel-loader',
            options: {
              presets: ['@babel/preset-env']
            }
          }
        }
      ]
    }
  }