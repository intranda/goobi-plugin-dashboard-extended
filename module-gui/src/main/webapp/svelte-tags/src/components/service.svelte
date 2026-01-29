<script>
    import { getContext } from "svelte";

    let { service = {} } = $props();

    const statusColorMap = getContext('statusColor');
    const color = $derived(statusColorMap.get(service.status));
</script>

<dt
    class="service {service.status}"
    title="{service.status}"
    style="--service-status-color: {color};">
    {service.name}: {service.status}
</dt>
<dd class="service-output">
    {service.output}
</dd>

<style>
    .service {
        border-inline-start: .25em solid var(--service-status-color);
        font-weight: normal;
        margin-inline-start: 0.5em;
        padding-inline-start: 2em;
    }

    .service,
    .service-output {
        height: 100%;
        padding-block: .5em;
    }

    .service:not(:last-of-type),
    .service-output:not(:last-of-type) {
        border-bottom: 1px solid var(--bs-border-color);
    }
</style>