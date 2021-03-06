/*
 * Copyright 2014-2016 michael-simons.eu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ac.simons.biking2.statistics.highcharts;

import ac.simons.biking2.support.Sink;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Michael J. Simons, 2014-02-11
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Options {

    @SuppressWarnings({"checkstyle:hiddenfield"})
    public static final class Builder<P> {

        private final Sink<P, Options> sink;

        private Chart chart;

        private Credits credits;

        private PlotOptions plotOptions;

        private Title title;

        private Tooltip tooltip;

        private Axis xAxis;

        private Axis yAxis;

        Builder(final Sink<P, Options> sink) {
            this.sink = sink;
        }

        public Chart.Builder<Builder<P>> chart() {
            return new Chart.Builder<>(newChart -> {
                Builder.this.chart = newChart;
                return Builder.this;
            });
        }

        public Credits.Builder<Builder<P>> credits() {
            return new Credits.Builder<>(newCredits -> {
                Builder.this.credits = newCredits;
                return Builder.this;
            });
        }

        public PlotOptions.Builder<Builder<P>> plotOptions() {
            return new PlotOptions.Builder<>(newPlotOptions -> {
                Builder.this.plotOptions = newPlotOptions;
                return Builder.this;
            });
        }

        public Title.Builder<Builder<P>> title() {
            return new Title.Builder<>(newTitle -> {
                Builder.this.title = newTitle;
                return Builder.this;
            });
        }

        public Tooltip.Builder<Builder<P>> tooltip() {
            return new Tooltip.Builder<>(newTooltip -> {
                Builder.this.tooltip = newTooltip;
                return Builder.this;
            });
        }

        public Axis.Builder<Builder<P>> xAxis() {
            return new Axis.Builder<>(newXAxis -> {
                Builder.this.xAxis = newXAxis;
                return Builder.this;
            });
        }

        public Axis.Builder<Builder<P>> yAxis() {
            return new Axis.Builder<>(newYAxis -> {
                Builder.this.yAxis = newYAxis;
                return Builder.this;
            });
        }

        public P build() {
            return this.sink.setObject(
                    new Options(chart, credits, plotOptions, title, tooltip, xAxis, yAxis)
            );
        }
    }

    private final Chart chart;

    private final Credits credits;

    private final PlotOptions plotOptions;

    private final Title title;

    private final Tooltip tooltip;

    private final Axis xAxis;

    private final Axis yAxis;

    @JsonCreator
    Options(
            @JsonProperty("chart") final Chart chart,
            @JsonProperty("credits") final Credits credits,
            @JsonProperty("plotOptions") final PlotOptions plotOptions,
            @JsonProperty("title") final Title title,
            @JsonProperty("tooltip") final Tooltip tooltip,
            @JsonProperty("xAxis") final Axis xAxis,
            @JsonProperty("yAxis") final Axis yAxis
    ) {
        this.chart = chart;
        this.credits = credits;
        this.plotOptions = plotOptions;
        this.title = title;
        this.tooltip = tooltip;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public Chart getChart() {
        return chart;
    }

    public Credits getCredits() {
        return credits;
    }

    public PlotOptions getPlotOptions() {
        return plotOptions;
    }

    public Title getTitle() {
        return title;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public Axis getxAxis() {
        return xAxis;
    }

    public Axis getyAxis() {
        return yAxis;
    }
}
