<rssfeed>

<div class="box box--primary box--padded">
    <div class="box__content">
        <div class="box__content-inner">
        
            <div class="box__title">
                <h2>
                    <i class="fa fa-globe "></i>
                    <span>{props.feedTitle}</span>
                </h2>
                
                <div class="actions d-flex">
                    <button 
                        type="button" 
                        class="btn d-flex align-items-center btn--title-action" 
                        onclick={loadFeed} 
                        title="{msg('reload')}">
                        <i class="fa fa-refresh"></i>
                    </button>
                    <a 
                        href="{props.feedUrl}" 
                        class="btn d-flex align-items-center btn--title-action" 
                        title="#{msg.rssSubscribe}" 
                        target="_blank"> 
                        <i class="fa fa-rss"></i>
                    </a>
                    <button 
                        type="button" 
                        class="btn d-flex align-items-center btn--title-action" 
                        onclick={toggleShow} 
                        title="{state.show ? msg('showBoxDetailsOff') : msg('showBoxDetailsOn')}">
                        <i class="fa {state.show ? 'fa-angle-down':'fa-angle-up'}" >
                        </i>
                    </button> 
                </div>
            </div>
        
            <div class="p-4 dashboardrss" if={state.show}>
                <div if={state.feed.length == 0} class="alert alert-info alert-dismissable">
    				{msg('dashboard_rssNotLoaded')}
    			</div>
    			
    			<div if={ state.feed.length != 0 }>
    				<div each={(entry, idx) in state.feed} styleClass="block">
    		
    					<div if={idx != 0}>
    						<hr class="mt-3 mb-3" style="color: var(--clr-neutral-400);"/>
    					</div>
    		
    					<div class="row">
    						<div class="col-sm-12">
    							<h3 style="font-size: 15px">
    								<a href="{entry.link}" target="_blank">
    									{entry.title}
    								</a>
    							</h3>
    						</div>
    					</div>
    		
    					<div class="row">
    						<div class="col-sm-12">
    							<raw html={entry.description} />
    						</div>
    					</div>
    				</div>
    			</div>
            </div>
            
        </div>
    </div>
</div>

<style>
	.dashboardrss img {
		max-height: 150px;
	}
</style>
<script>
import Raw from './raw.tag'
 
export default {
    components: {
        Raw
    },
    onBeforeMount(props, state) {
      var show = sessionStorage.getItem('goobi_dashboard_show_rss') || "true";
      this.state = {
      	  feed: [],
      	  show: show == "true"
      };
      if(this.state.show) {
	      this.loadFeed();
      }
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
    loadFeed() {
       if(this.state.feed.length > 0) {
	       this.state.feed = [];
	       this.update();
       } 
       var path = window.location.pathname;
       path = path.substring(0, path.indexOf("/uii/"));
       fetch(`${path}/plugins/exdashboard/rssfeed`).then(resp => {
        resp.json().then(json => {
          this.state.feed = json;
          this.update();
        })
      });
    },
    toggleShow() {
        this.state.show = !this.state.show;
        sessionStorage.setItem('goobi_dashboard_show_rss', this.state.show);
        if(this.state.show && this.state.feed.length == 0) {
            this.loadFeed();
        }
        this.update();
    }
}
</script>

</rssfeed>