<nagios>

<div class="box box--primary box--padded">
    <div class="box__content">
        <div class="box__content-inner">
        
            <div class="box__title">
                <h2>
                    <i class="fa fa-search "></i>
                    Style Guide
                </h2>
                
                <div class="actions d-flex">
                    <button  
                        type="submit" 
                        value="" 
                        title="This is my title" 
                        class="btn d-flex align-items-center btn--title-action">
                        <i class="fa fa-plus"></i>
                    </button>
                </div>
            </div>
        
            <div class="p-4">
                Box with menu button
            </div>
            
        </div>
    </div>
</div>


<div class="box box-color lightgrey box-bordered">
	<div class="box-title">
		<h2>
			<i class="fa fa-binoculars"></i>
			Monitoring
		</h2>

		<div class="actions">

			<!-- reload all data again -->
			<a class="btn btn-mini" onclick={reload} title="{msg('reload')}">
				<i class="fa fa-refresh"></i>
			</a>
			<!-- // reload all data again -->

			<!-- switch view -->
			<a class="btn btn-mini" onclick={toggleShow}
				title="{state.show == 'up'? msg('showBoxDetailsOn') : msg('showBoxDetailsOff')}">
				<i class="fa {state.show == 'down'? 'fa-angle-up' : 'fa-angle-down'}"></i>
			</a>
			<!-- // switch view -->

		</div>

	</div>
	<div class="box-content nopadding">
		<div style="padding:20px;" if={state.hosts.length == 0}>
			<span class="alert alert-info alert-dismissable margin-bottom-10">
				{msg('dashboard_nagiosNotLoaded')}
			</span>
		</div>
	
		<div class="padding:10px;" if={state.hosts.length != 0}>
	
			<div if={state.show != 'down'} style="padding:10px;">
	
	
				<div each={host in state.hosts} key={host.name}>
						<div style="height: 1px; background-color: #cccccc; text-align: left; margin: 25px 20px 40px 0px;">
							<div class="label {host.summary == 'OK'?'label-intranda-green':host.summary == 'WARNING'?'label-intranda-orange':'label-intranda-red'}" style="position: relative; top: -1em; font-size: 14px; font-weight: normal; padding: 5px; display: inline-block; border: white solid 5px;">
								<i class="fa {host.summary == 'OK'?'fa-check':host.summary == 'WARNING'?'fa-exclamation-triangle':'fa-times-circle'}"/>
								<span style="margin:5px;">{host.name}</span>
							</div>
						</div>
					
						<div if={host.summary != 'OK' && host.nagios != null} style="margin-top:-15px;">
							<span each={ mystatus in host.nagios.status.service_status} key={mystatus.service_description} 
								class="badge {mystatus.badgeColor} font-size-xs" style="font-weight:normal; margin:3px;" 
								title="{mystatus.status_information}" rel="tooltip">
								{mystatus.service_display_name}
							</span>
						</div>
				</div>
			</div>
	
			<div if={state.show == 'down'}>
				<table class="table table-hover responsive">
					<tbody>
						<template each={host in state.hosts}>
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
							<tr each={mystatus in host.nagios.status.service_status}>
								<td>
									<span class="badge badge-intranda-green" style="font-weight:normal"
										if={mystatus.status == 'OK' || mystatus.state_type == 'SOFT'} >
										{mystatus.service_display_name}
									</span>
									<span class="badge badge-intranda-orange" style="font-weight:normal" 
										if={mystatus.status == 'WARNING' && mystatus.state_type == 'HARD'} >
										{mystatus.service_display_name}
									</span>
									<span class="badge badge-intranda-red" style="font-weight:normal"
										if={mystatus.status == 'CRITICAL' && mystatus.state_type == 'HARD'} >
										{mystatus.service_display_name}
									</span>
								</td>
								<td>{mystatus.status_information}</td>
							</tr>
						</template>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script>
export default {
    onBeforeMount(props, state) {
      console.log(props)
      this.state = {
      	  hosts: []
      };
      this.reload();
    },
    onMounted(props, state) {
      
    },
    onBeforeUpdate(props, state) {
      
    },
    onUpdated(props, state) {
      
    },
    onBeforeUnmount(props, state) {
      console.log("unmounting - stop polling");
    },
    msg(str) {
      if(Object.keys(this.props.msgs).length == 0) {
          return "*".repeat(str.length);
      }
      if(this.props.msgs[str]) {
        return this.props.msgs[str];
      }
      return "???" + str + "???";
    },
    toggleShow() {
        console.log("toggling show")
        if(this.state.show == "down") {
        	this.state.show = "up";
        } else {
            this.state.show = "down";
        }
        this.update();
    },
    badgeColor(status) {
        if(status.status == 'OK' || status.state_type == 'SOFT') {
            return "badge-intranda-green";
        }
        if(status.status == 'WARNING') {
            return "badge-intranda-orange";
        }
        return "badge-intranda-red";
    },
    reload() {
      if(this.state.hosts.length > 0) {
	      this.state.hosts = [];
	      this.update();
      }
	  const goobi_path = location.pathname.split('/')[1];
      fetch(`/${goobi_path}/api/plugins/exdashboard/nagios`).then(resp => {
          resp.json().then(json => {
            console.log(json)
            this.state.hosts = json;
            for(var host of this.state.hosts) {
                if(host.nagios) {
                	for(var check of host.nagios.status.service_status) {
                	    check.badgeColor = this.badgeColor(check);
                	}
                }
            }
            this.update();
          })
        });
    }
}
</script>

</nagios>