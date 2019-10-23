<itmview>
<div class="box box-color lightgrey box-bordered">
	<div class="box-title">
		<h3>
			<i class="fa fa-briefcase"></i>
			<span>"intranda TaskManager:</span>
			<span if="{state.currentItmPlugin == null}">Plugins</span>
			<span if="{state.currentItmPlugin != null}">{state.currentItmPlugin.name}</span>
		</h3>

		<div class="actions">
		
			<!-- reload all data again -->
			<a class="btn btn-mini" title="{msg('reload')}" onclick={reload}>
				<i class="fa fa-refresh" />
			</a>
			<!-- // reload all data again -->
			
			<!-- close current queue and show all itm plugins again -->
			<a class="btn btn-mini" title="{msg('uebersicht')}"
				if="{state.currentItmPlugin != null}"
				onclick={showAll}>
				<i class="fa fa-desktop" />
			</a>
			<!-- // close current queue and show all itm plugins again -->
		</div>
		
		<!-- show tabs to select between job status -->
		<div if="#{state.currentItmPlugin != null}">
			<ul class="tabs">
				<li class="{state.currentQueueName == 'processing'?'active':''}">
					<a onclick={() => setQueue('processing')} style="cursor: pointer;">
						Processing
					</a>
				</li>
				<li class="{state.currentQueueName == 'done'?'active':''}">
					<a onclick={() => setQueue('done')} style="cursor: pointer;">
						Done
					</a>
				</li>
				<li class="{state.currentQueueName == 'error'?'active':''}">
					<a onclick={() => setQueue('error')} style="cursor: pointer;">
						Error
					</a>
				</li>
			</ul>
		</div>
		<!-- // show tabs to select between job status -->

	</div>
				
	<div class="box-content nopadding">
		<div style="padding:20px;display:block;" if="{state.itmPlugins.length == 0}">
			<span class="alert alert-info alert-dismissable margin-bottom-10">
				{msg('dashboard_noItmConnection')}
			</span>
		</div>
		
		<!-- no queue loaded - show list of all itm plugins -->
		<div if="{state.itmPlugins.length != 0 && state.currentItmPlugin == null}" >
			
			<table class="table table-hover responsive">
				<tbody>
					<tr each={item in state.itmPlugins} style="cursor: pointer;" onclick={ () => setCurrentItmPlugin(item)}>
						<td>
							<a class="font-black" rel="tooltip" title="{item.strInt.str}">
								{item.strInt.str}
							</a>
						</td>
						<td>
							<a class="font-black" rel="tooltip" title="{item.strInt.number} Jobs">
								{item.strInt.number} Jobs
							</a>
						</td>
						<td>
							<a class="pull-right margin-right-regular" rel="tooltip" title="{item.running}">
								<span class="badge font-size-xs {item.running == 'running'?'badge-intranda-green':'badge-intranda-red'}" style="font-weight:normal">{item.running}</span>
							</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- // no queue loaded - show list of all itm plugins -->
		
		<!-- one queue is selected - show details -->
		<div if={state.itmPlugins.length != 0 && state.currentItmPlugin != null && state.currentQueueName != 'error'} >
			<table class="table table-hover responsive">
				<div style="padding:20px;display:block;" if={state.currentList.length == 0}>
					<span class="alert alert-info alert-dismissable margin-bottom-10">
						{msg('dashboard_noJobsInList')}
					</span>
				</div>
				<tbody>
					<tr each={item in state.currentList}>
						<td>{item.goobiId}</td>
						<td class="tableColumnMaxWidthLong" title="#{item.goobiProcessTitle}">{item.goobiProcessTitle}</td>
						<td>{item.size}</td>
						<td>{item.abbyyPages}</td>
						<td class="tableColumnMaxWidthLong" title="#{item.status}">{item.status}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div if="{state.itmPlugins.length != 0 && state.currentItmPlugin != null && state.currentQueueName == 'error'}" >
			<table class="table table-hover responsive">
				<tbody>
					<tr each={item in state.currentList}>
						<td>{item.goobiId}</td>
						<td class="tableColumnMaxWidthLong" title="{item.goobiProcessTitle}">{item.goobiProcessTitle}</td>
						<td class="tableColumnMaxWidthLong" title="{item.errorMessage}" >{item.errorMessage}</td>
						<td class="tableColumnMaxWidthLong" title="{item.status}">{item.status}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- // one queue is selected - show details -->
	</div>
</div>
<script>
export default {
    onBeforeMount(props, state) {
      this.state = {
          itmPlugins: [],
          currentItmPlugin: null,
          currentList: [],
          currentQueueName: "done"
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
    setCurrentItmPlugin(job) {
        this.state.currentItmPlugin = job;
        this.setQueue(null);
        this.update();
    },
    showAll() {
        this.state.currentItmPlugin = null;
        this.update();
    },
    reload() {
        fetch(`/goobi/plugins/exdashboard/itm`).then(resp => {
	        resp.json().then(json => {
	          this.state.itmPlugins = json;
	          this.update();
	    	})
		});
    },
    setQueue(queue) {
        if(queue != null) {
        	this.state.currentQueueName = queue;
        }
        switch(this.state.currentQueueName) {
            case "processing":
                this.state.currentList = this.state.currentItmPlugin.listProcessing;
                break;
            case "done":
                this.state.currentList = this.state.currentItmPlugin.listDone;
                break;
            case "error":
                this.state.currentList = this.state.currentItmPlugin.listError;
                break;
        }
        this.update();
    }
}
</script>
</itmview>