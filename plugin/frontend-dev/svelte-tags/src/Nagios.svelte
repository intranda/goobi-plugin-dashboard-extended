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
      fetch("/goobi/plugins/exdashboard/nagios").then(resp => {
          resp.json().then(json => {
            hosts = json;
            for(var host of hosts) {
                if(host.nagios) {
                	for(var check of host.nagios.status.service_status) {
                	    check.badgeColor = badgeColor(check);
                	}
                }
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
        if(status.status == 'OK' || status.state_type == 'SOFT') {
            return "badge-intranda-green";
        }
        if(status.status == 'WARNING') {
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

<div class="box box-color lightgrey box-bordered">
	<div class="box-title">
		<h2>
			<i class="fa fa-binoculars"></i>
			Monitoring
		</h2>

		<div class="actions">

			<!-- reload all data again -->
			<a class="btn btn-mini" on:click={reload} title="{msg('reload')}">
				<i class="fa fa-refresh"></i>
			</a>
			<!-- // reload all data again -->

			<!-- switch view -->
			<a class="btn btn-mini" on:click={toggleShow}
				title="{show == 'up'? msg('showBoxDetailsOn') : msg('showBoxDetailsOff')}">
				<i class="fa {show == 'down'? 'fa-angle-up' : 'fa-angle-down'}"></i>
			</a>
			<!-- // switch view -->

		</div>

	</div>
	<div class="box-content nopadding">
		{#if hosts.length == 0}
		<div style="padding:20px;">
			<span class="alert alert-info alert-dismissable margin-bottom-10">
				{msg('dashboard_nagiosNotLoaded')}
			</span>
		</div>
		{/if}
	
		{#if hosts.length != 0}
		<div class="padding:10px;" >
			{#if show != 'down'}
			<div style="padding:10px;">
	
				{#each hosts as host}
				<div>
						<div style="height: 1px; background-color: #cccccc; text-align: left; margin: 25px 20px 40px 0px;">
							<div class="label {host.summary == 'OK'?'label-intranda-green':host.summary == 'WARNING'?'label-intranda-orange':'label-intranda-red'}" style="position: relative; top: -1em; font-size: 14px; font-weight: normal; padding: 5px; display: inline-block; border: white solid 5px;">
								<i class="fa {host.summary == 'OK'?'fa-check':host.summary == 'WARNING'?'fa-exclamation-triangle':'fa-times-circle'}"/>
								<span style="margin:5px;">{host.name}</span>
							</div>
						</div>
					
						{#if host.summary != 'OK' && host.nagios != null}
						<div  style="margin-top:-15px;">
							{#each host.nagios.status.service_status as mystatus}
							<span
								class="badge {mystatus.badgeColor} font-size-xs" style="font-weight:normal; margin:3px;" 
								title="{mystatus.status_information}" rel="tooltip">
								{mystatus.service_display_name}
							</span>
							{/each}
						</div>
						{/if}
				</div>
				{/each}
			</div>
			{/if}
	
			{#if show == 'down'} 
			<div>
				<table class="table table-hover responsive">
					<tbody>
						{#each hosts as host}
							<tr>
								<td colspan="2">
									<div class="label {host.summary == 'OK'?'label-intranda-green':host.summary == 'WARNING'?'label-intranda-orange':'label-intranda-red'}" style="font-size: 14px; font-weight: normal; padding: 5px; margin: 0px;display:block;">
										<i class="fa {host.summary == 'OK'?'fa-check':host.summary == 'WARNING'?'fa-exclamation-triangle':'fa-times-circle'}"/>
										<span style="margin:5px;">
											{host.name}
										</span>
									</div>
									
								</td>
							</tr>
							{#each host.nagios.status.service_status as mystatus}
							<tr>
								<td>
									{#if mystatus.status == 'OK' || mystatus.state_type == 'SOFT'}
									<span class="badge badge-intranda-green" style="font-weight:normal">
										{mystatus.service_display_name}
									</span>
									{/if}
									{#if mystatus.status == 'WARNING' && mystatus.state_type == 'HARD'}
									<span class="badge badge-intranda-orange" style="font-weight:normal">
										{mystatus.service_display_name}
									</span>
									{/if}
									{#if mystatus.status == 'CRITICAL' && mystatus.state_type == 'HARD'}
									<span class="badge badge-intranda-red" style="font-weight:normal">
										{mystatus.service_display_name}
									</span>
									{/if}
								</td>
								<td>{mystatus.status_information}</td>
							</tr>
							{/each}
						{/each}
					</tbody>
				</table>
			</div>
			{/if}
		</div>
		{/if}
	</div>
</div>

<style>

</style>