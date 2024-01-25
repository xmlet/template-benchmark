# Labels
set ylabel 'Templates rendered per Ms'
set xlabel 'Template Engine'
set xtics nomirror rotate by -45

# Ranges
set autoscale

# Input
set datafile separator ','

# Output
set terminal svg enhanced standalone
set grid
set key off

# box style
set style line 1 lc rgb '#5C91CD' lt 1
set style fill solid

# remove top and right borders
set style line 2 lc rgb '#808080' lt 1
set border 3 back ls 2
set tics nomirror


files = 'stocks presentations'

do for [dataset in files] {
    set title dataset
    set output 'results-'.dataset.'.svg'

    plot 'results-'.dataset.'.csv' every ::1 using 0:5:(0.8):0:xticlabels(stringcolumn(1)[31:50]) with boxes lc variable, \
         'results-'.dataset.'.csv' every ::1 using 0:($5 + 5):(sprintf("%d",$5)) with labels

    set output
}
