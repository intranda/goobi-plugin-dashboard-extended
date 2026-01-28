<script>
	import { onMount } from 'svelte';

	//HOOKS
	onMount( () => {
		reload();
	});

	// PROPS
	export let msgs;

	// STATE
	let hosts = [];
	let show = 'up';

	//METHODS
	let reload = () => {
		if(hosts.length > 0) {
			hosts = [];
		}
		var path = window.location.pathname;
		path = path.substring(0, path.indexOf("/uii/"));
		fetch(`${path}/api/plugins/exdashboard/icinga2`).then(resp => {
			resp.json().then(json => {
				hosts = json;
				for(var host of hosts) {
					hosts.servicesByStatus = {};
					for(var service of host.services) {
						service.badgeColor = badgeColor(service.status);
						hosts.servicesByStatus[service.status] = hosts.servicesByStatus[service.status] || [];
						hosts.servicesByStatus[service.status].push(service);
					}
					host.summary = 'OK';
					if (host.status === 'DOWN') {
						host.summary = 'CRITICAL';
					} else {
						if ('CRITICAL' in hosts.servicesByStatus.keys && hosts.servicesByStatus['CRITICAL'].length > 0) {
							host.summary = 'CRITICAL';
						} else if ('WARNING' in hosts.servicesByStatus.keys && hosts.servicesByStatus['WARNING'].length > 0) {
							host.summary = 'WARNING';
						}
					}
					host.badgeColor = badgeColor(host.summary);
					host.icon = host.summary === 'OK'?'fa-check':(host.summary === 'WARNING'?'fa-exclamation-triangle':'fa-times-circle');
				}
			})
		});
	}
	let msg = (str) => {
		if(Object.keys(msgs).length == 0) {
			return "*".repeat(str.length);
		}
		if(msgs[str]) {
			return msgs[str];
		}
		return "???" + str + "???";
	}
	let badgeColor = (status) => {
		if(status == 'OK' ) {
			return "badge-intranda-green";
		}
		if(status == 'WARNING') {
			return "badge-intranda-orange";
		}
		return "badge-intranda-red";
	}
	let toggleShow = () => {
		if(show == "up") {
			show = "down";
		} else {
			show = "up"
		}
	}
</script>


<div class="box box--neutral box--padded">
	<div class="box__content">
		<div class="box__content-inner">

			<div class="box__title">
				<h2>
					<i class="fa fa-binoculars "></i>
					Monitoring
				</h2>

				<div class="actions d-flex">

					<!-- reload all data again -->
					<a class="btn d-flex align-items-center btn--title-action" on:click={reload} title="{msg('reload')}">
						<i class="fa fa-refresh"></i>
					</a>
					<!-- // reload all data again -->

					<!-- switch view -->
					<a class="btn d-flex align-items-center btn--title-action" on:click={toggleShow}
					   title="{show == 'up'? msg('showBoxDetailsOn') : msg('showBoxDetailsOff')}">
						<i class="fa {show == 'down'? 'fa-angle-up' : 'fa-angle-down'}"></i>
					</a>
					<!-- // switch view -->

				</div>
			</div>

			<div class="p-0">
				{#if hosts.length == 0}
				<div class="p-3">
					<div class="alert alert-info alert-dismissable">
						{msg('dashboard_Icinga2NotLoaded')}
					</div>
				</div>
				{/if}

				{#if hosts.length != 0}
				<div>
					<div>
					{#each hosts as host}
						<div>
							<div style="height: 0.005rem; background-color: var(--clr-neutral-400); text-align: left; margin: 15px 20px 40px 0px;">
								<div class="badge {host.badgeColor}" style="position: relative; top: -1em; font-size: 14px; font-weight: normal; padding: 5px; display: inline-block; border: white solid 5px;">
									<i class="fa {host.icon}"/>
									<span style="margin:5px;">{host.name}</span>
								</div>
							</div>

							<div  style="margin-top:-15px;">
								{#each host.servicesByStatus['UNKNOWN'] as service}
								<span
										class="badge {service.badgeColor}" style="font-weight:normal; margin:3px;"
										title="{service.status}" rel="tooltip">
									{service.name} / {service.output}
								</span>
								{/each}
								{#each host.servicesByStatus['CRITICAL'] as service}
								<span
										class="badge {service.badgeColor}" style="font-weight:normal; margin:3px;"
										title="{service.status}" rel="tooltip">
									{service.name} / {service.output}
								</span>
								{/each}
								{#each host.servicesByStatus['WARNING'] as service}
								<span
										class="badge {service.badgeColor}" style="font-weight:normal; margin:3px;"
										title="{service.status}" rel="tooltip">
									{service.name} / {service.output}
								</span>
								{/each}
								{#if show == 'down'}
								{#each host.servicesByStatus['OK'] as service}
								<span
										class="badge {service.badgeColor}" style="font-weight:normal; margin:3px;"
										title="{service.status}" rel="tooltip">
									{service.name}
								</span>
								{/each}
								{/if}
							</div>
						</div>
					{/each}
					</div>
				</div>
				{/if}
			</div>
		</div>
	</div>
</div>
