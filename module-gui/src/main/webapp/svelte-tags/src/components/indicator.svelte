<script>
    import { getContext } from "svelte";

    let { status } = $props();

    const statusColorMap = getContext('statusColor');
    const color = $derived(statusColorMap.get(status));
</script>

<span aria-hidden="true" class="status-indicator status-indicator-{status}" style="--status-indicator-color: {color};">
    <span class="status-indicator-circle"></span>
    <span class="status-indicator-circle"></span>
    <span class="status-indicator-circle"></span>
</span>

<style>
    .status-indicator {
        --status-indicator-size: 1.25em;
        border-radius: 50%;
        display: block;
        flex: 0 0 auto;
        height: var(--status-indicator-size);
        position: relative;
        width: var(--status-indicator-size);
    }

    .status-indicator:not(.status-indicator-OK) .status-indicator-circle:first-child {
        animation: 2s linear 1s infinite backwards status-pulsate-main;
        z-index: 3;
    }

    .status-indicator:not(.status-indicator-OK) .status-indicator-circle:nth-child(2) {
       animation: 2s linear 1s infinite backwards status-pulsate-secondary;
       opacity: 0.1;
       z-index: 2;
    }

    .status-indicator:not(.status-indicator-OK) .status-indicator-circle:nth-child(3) {
        animation: 2s linear 1s infinite backwards status-pulsate-tertiary;
        opacity: 0.3;
        z-index: 1;
    }

    .status-indicator-circle {
        background: var(--status-indicator-color);
        border-radius: 50%;
        display: block;
        height: 100%;
        position: absolute;
        width: 100%;
    }

    @keyframes status-pulsate-main {
        40% {
            transform: scale(1.25, 1.25);
        }
        60% {
            transform: scale(1.25, 1.25);
        }
    }

    @keyframes status-pulsate-secondary {
        10% {
            transform: scale(1, 1);
        }
        30% {
            transform: scale(2, 2);
        }
        80% {
            transform: scale(2, 2);
        }
        100% {
            transform: scale(1, 1);
        }
    }

    @keyframes status-pulsate-tertiary {
        25% {
            transform: scale(1, 1);
        }
        80% {
            transform: scale(2, 2);
            opacity: 0;
        }
        100% {
            transform: scale(2, 2);
            opacity: 0;
        }
    }

</style>