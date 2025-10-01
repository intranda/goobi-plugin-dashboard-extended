/**
 * Chart utility functions for Goobi Dashboard Plugin
 */

// Default chart options
let defaultChartOptions = {
    options: {
        plugins: {
            title: {
                display: false,
                text: "Processes per month"
            },
            legend: {
                display: false
            }
        }
    }
};

/**
 * Standard chart extender function
 */
function chartExtender() {
    let options = $.extend(true, {}, this.cfg.config);
}

/**
 * Calculate tick values for logarithmic scale based on data range
 * @param {number} maxValue - The maximum value in the dataset
 * @returns {Array} Array of tick values to display
 */
const calculateLogTicks = (maxValue) => {
    let ticks = [];
    if (maxValue <= 0) return [1, 10];

    // Find the highest power of 10 needed
    let maxPower = Math.ceil(Math.log10(maxValue));

    // Always start with 1
    ticks.push(1);

    // Add powers of 10 up to the max needed
    for (let i = 1; i <= maxPower; i++) {
        ticks.push(Math.pow(10, i));
    }

    return ticks;
}

/**
 * Chart extender function for logarithmic scale with dynamic ticks
 */
function chartExtenderLog() {
    let options = $.extend(true, {}, this.cfg.config);

    options.options = {
        scales: {
            y: {
                type: 'logarithmic',
                min: 0,
                ticks: {
                    callback: function(value, index, values) {
                        // Get the maximum value from the chart data
                        let maxValue = 0;
                        if (this.chart && this.chart.data && this.chart.data.datasets) {
                            this.chart.data.datasets.forEach(dataset => {
                                if (dataset.data) {
                                    dataset.data.forEach(dataPoint => {
                                        maxValue = Math.max(maxValue, dataPoint);
                                    });
                                }
                            });
                        }

                        // Calculate ticks
                        let calculatedTicks = calculateLogTicks(maxValue);

                        // Show only the calculated tick values
                        if (calculatedTicks.includes(value)) {
                            return value;
                        }
                        return '';
                    }
                }
            }
        }
    };

    $.extend(true, this.cfg.config, options);
}
