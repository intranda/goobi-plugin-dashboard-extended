<itmview>

<div class="box box--neutral box--padded">
    <div class="box__content">
        <div class="box__content-inner">
        
            <div class="box__title">
                <h2>
                    <i class="fa fa-briefcase"></i>
                    <span>intranda TaskManager</span>
        			<span if="{state.currentItmPlugin == null}">:&nbsp;Plugins</span>
        			<span if="{state.currentItmPlugin != null}">{state.currentItmPlugin.name}</span>
                </h2>
                
                <div class="actions d-flex">
                    
                    <!-- show buttons to select between job status -->                
                    <a if="#{state.currentItmPlugin != null}"
                        class="btn d-flex align-items-center btn--title-action {state.currentQueueName == 'processing'?'active':''}" 
                        title="Processing"
                        onclick={() => setQueue('processing')}>
                        <i class="fa fa-cog"></i>
                    </a>
                
                    <a if="#{state.currentItmPlugin != null}"
                        class="btn d-flex align-items-center btn--title-action {state.currentQueueName == 'done'?'active':''}"
                        title="Done"
                        onclick={() => setQueue('done')}>
                        <i class="fa fa-check-circle-o"></i>
                    </a>
               
                    <a if="#{state.currentItmPlugin != null}"
                        class="btn d-flex align-items-center btn--title-action {state.currentQueueName == 'error'?'active':''}"
                        title="Error"
                        onclick={() => setQueue('error')}>
                        <i class="fa fa-exclamation-circle"></i>
                    </a>
                    <!-- // show buttons to select between job status -->

                    <!-- reload all data again -->
                    <a class="btn d-flex align-items-center btn--title-action" title="{msg('reload')}" onclick={reload}>
                        <i class="fa fa-refresh"></i>
                    </a>
                    <!-- // reload all data again -->
                    
                    <!-- close current queue and show all itm plugins again -->
                    <a class="btn d-flex align-items-center btn--title-action" title="{msg('uebersicht')}"
                        if="{state.currentItmPlugin != null}"
                        onclick={showAll}>
                        <i class="fa fa-desktop"></i>
                    </a>
                    <!-- // close current queue and show all itm plugins again -->
                    
                </div>
            </div>
        
            <div class="p-0">
                
                <div if={state.itmPlugins.length == 0} class="alert alert-info alert-dismissable m-3">
                    {msg('dashboard_noItmConnection')}
                </div>
            	
        		<!-- no queue loaded - show list of all itm plugins -->
                <div if="{state.itmPlugins.length != 0 && state.currentItmPlugin == null}"
                    class="gw-table gw-table-fixed">
                    <div class="gw-table__wrapper">
                        <table role="grid" id="tableUserAll" class="table table-bordered table-fixed table-hover">
                            <tbody>
                                <tr role="row" each={item in state.itmPlugins} style="cursor: pointer;" onclick={ () => setCurrentItmPlugin(item)}>
                					<td>
            							{item.strInt.str}
                					</td>
                					<td>
            							{item.strInt.number} Jobs
                					</td>
                					<td>
                						<a class="float-end" rel="tooltip" title="{item.running}">
                                        	<span class="badge {item.running == 'running'?'badge-intranda-green':'badge-intranda-red'}" style="font-weight:normal">{item.running}</span>
                						</a>
                					</td>
                				</tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- // no queue loaded - show list of all itm plugins -->
        		
        		<!-- one queue is selected - show details -->
        		<div if={state.itmPlugins.length != 0 && state.currentItmPlugin != null && state.currentQueueName != 'error'} 
                    class="gw-table gw-table-fixed">
        			<div class="gw-table__wrapper">
                    
                        <table role="grid" class="table table-bordered table-fixed table-hover">
            				
                            <div if={state.currentList.length == 0} class="alert alert-info alert-dismissable m-3">
            						{msg('dashboard_noJobsInList')}
            				</div>
            				<tbody>
            					<tr role="row" each={item in state.currentList}>
            						<td>{item.goobiId}</td>
            						<td title="#{item.goobiProcessTitle}">{item.goobiProcessTitle}</td>
            						<td>{item.size}</td>
            						<td>{item.abbyyPages}</td>
            						<td title="#{item.status}">{item.status}</td>
            					</tr>
            				</tbody>
            			</table>
                    </div>
        		</div>
        		
        		<div if="{state.itmPlugins.length != 0 && state.currentItmPlugin != null && state.currentQueueName == 'error'}" 
                    class="gw-table gw-table-fixed">
                    <div class="gw-table__wrapper">
            			<table role="grid" class="table table-bordered table-fixed table-hover">
            				<tbody>
            					<tr role="row" each={item in state.currentList}>
            						<td>{item.goobiId}</td>
            						<td title="{item.goobiProcessTitle}">{item.goobiProcessTitle}</td>
            						<td title="{item.errorMessage}" >{item.errorMessage}</td>
            						<td title="{item.status}">{item.status}</td>
            					</tr>
            				</tbody>
            			</table>
                    </div>
        		</div>
        		<!-- // one queue is selected - show details -->
            </div>
            
        </div>
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
        if(this.state.itmPlugins.length > 0) {
            this.state.itmPlugins = [];
            this.update();
        }
        const goobi_path = location.pathname.split('/')[1];
        fetch(`/${goobi_path}/api/plugins/exdashboard/itm`).then(resp => {
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