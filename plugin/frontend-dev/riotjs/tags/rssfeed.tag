<rssfeed>
<div class="box box-color box-bordered">
		<div class="box-title">
			<h2>
				<i class="fa fa-globe"></i>
				<span>{props.feedTitle}</span>
			</h2>
				<div class="actions">
					<button type="button" class="btn btn-mini" onclick={loadFeed} title="{msg('reload')}">
                        <span class="fa fa-refresh"></span>
                    </button>
					<a href="{props.feedUrl}" class="btn btn-mini" title="#{msg.rssSubscribe}" target="_blank"> <i class="fa fa-rss" />
					</a>
                    <button type="button" class="btn btn-mini" onclick={toggleShow} style="cursor: pointer;"
                        title="{state.show ? msg('showBoxDetailsOff') : msg('showBoxDetailsOn')}">
                        <span class="fa {state.show ? 'fa-angle-down':'fa-angle-up'}" ></span>
                    </button> 
				</div>
		</div>
		<div class="box-content dashboardrss" if={state.show}>
			<div if={state.feed.length == 0} class="alert alert-info alert-dismissable margin-bottom-10">
				{msg('dashboard_rssNotLoaded')}
			</div>
			
			<div class="padding:10px;display:block;" if={ state.feed.length != 0 }>
				<div each={(entry, idx) in state.feed} styleClass="margin-sides-10 block">
		
					<div if={idx != 0}>
						<hr style="margin: 10px" />
					</div>
		
					<div class="row">
						<div class="col-sm-12">
							<h3 style="font-size: 18px">
								<a href="{entry.link}" target="_blank">
									{entry.title}
								</a>
							</h3>
						</div>
					</div>
		
					<div class="row margin-top-more">
						<div class="col-sm-12">
							<raw html={entry.description} />
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
       fetch(`/goobi/plugins/exdashboard/rssfeed`).then(resp => {
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