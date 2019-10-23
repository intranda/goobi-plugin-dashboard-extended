import * as riot from 'riot'
import Rssfeed from './tags/rssfeed.tag'
import Itmview from './tags/itmview.tag'

const mountRssFeed = riot.component(Rssfeed)

const mountItmView = riot.component(Itmview)

window.mountRssFeed = mountRssFeed;
window.mountItmView = mountItmView;
