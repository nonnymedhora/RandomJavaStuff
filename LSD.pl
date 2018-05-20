#!/usr/bin/perl

$string = "ABCDE"; ##$ARGV[0];

srand($$|time);

@chars = split(//, $string);

$length = $#chars + 1;

for($i = 0; $i < $length; $i++) {
    for($j = 0; $j < $length; $j++) {
        $the_char = &pick_random_char($i,$j);
        if ($the_char != -1) {
            $matrix[$i][$j] = $the_char;
        } else {
            $i = 0;
            $j = -1;
            for($x = 0; $x < $length; $x++) {
                for($y = 0; $y < $length; $y++) {
                    $matrix[$x][$y] = '';
                }
            }
        }
    }
}

print "\n";
for($i = 0; $i < $length; $i++) {
    for($j = 0; $j < $length; $j++) {
        print "$matrix[$i][$j] ";
    }
    print "\n";
}

sub pick_random_char() {
    my($x, $y) = @_;
    my($char);
    my($ok) = 0;
    my($i);
    my($stuck) = 0;
   
    while(!$ok) {
        $stuck++;
        $char = @chars[int(rand($length))];
        $horiz_ok = 1;
        for($i = 0; $i < $length; $i++) {
            if ($matrix[$x][$i] eq "$char") {
                $horiz_ok = 0;
            }
        }
        $vert_ok = 1;
        for($i = 0; $i < $length; $i++) {
            if ($matrix[$i][$y] eq "$char") {
                $vert_ok = 0;
            }
        }
        if ($vert_ok && $horiz_ok) {
            $ok = 1;
        }
        if ($stuck > 100) {
            $ok = 1;
            $char = -1;           
        }
    }
    return($char);
}
