import * as riot from 'riot'
import Rssfeed from './tags/rssfeed.tag'
import Itmview from './tags/itmview.tag'
import Nagios from './tags/nagios.tag'

const mountRssFeed = riot.component(Rssfeed)
const mountItmView = riot.component(Itmview)
const mountNagios = riot.component(Nagios)

window.mountRssFeed = mountRssFeed;
window.mountItmView = mountItmView;
window.mountNagios = mountNagios;
