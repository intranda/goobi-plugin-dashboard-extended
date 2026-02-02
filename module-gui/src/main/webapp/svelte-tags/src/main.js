import { mount } from 'svelte';
import Nagios from './Nagios.svelte';
import Icinga2 from './Icinga2.svelte';


window.mountSvelteNagios = (options) => mount(Nagios, options);
window.mountSvelteIcinga2 = (options) => mount(Icinga2, options);