/**
 * plugins/index.js
 *
 * Automatically included in `./src/main.js`
 */

// Plugins
import { loadFonts } from './webfontloader'
import vuetify from './vuetify'
import './axios'

export function registerPlugins (app) {
  loadFonts()
  app.use(vuetify)
}
