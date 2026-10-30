#!/usr/bin/env python3
"""Markdown table of the bench runs (tools/bench.sh): build/bench/before.txt and after.txt, the mean of every window of a
mode. The base line is the mean of the base windows of both files (the mod's tick handlers off the event bus).
Usage: tools/bench_table.py [before.txt after.txt]"""
import re
import sys
from collections import defaultdict
from pathlib import Path

BENCH = Path(__file__).resolve().parent.parent / 'build/bench'
METRICS = [('mspt', 'MSPT, мс'), ('fps', 'FPS'), ('player_server_us', 'тик игрока, сервер, мкс'), ('player_client_us', 'тик игрока, клиент, мкс'),
           ('entity_server_us', 'тик сущностей, сервер, мкс'), ('level_us', 'тик мира, мкс'), ('syncs_per_s', 'пакетов синхронизации в секунду')]
PHASES = {'overworld': 'Обычный мир', 'deep_void': 'Deep Void', 'deep_void_mobs': 'Deep Void + сталкеры и оверсир'}


def read(path):
    windows = defaultdict(list)
    for line in path.read_text().splitlines():
        m = re.match(r'\[bench\] phase=(\S+) mode=(\S+) (.*)', line)
        if m and int(re.search(r'ticks=(\d+)', line).group(1)) >= 150:  # a window the server mostly missed is no measurement
            windows[(m.group(1), m.group(2))].append(dict(kv.split('=') for kv in m.group(3).split()))
    return windows


def mean(rows, key):
    values = [float(r[key]) for r in rows]
    return sum(values) / len(values) if values else float('nan')


def main():
    before, after = (Path(a) for a in sys.argv[1:3]) if len(sys.argv) > 2 else (BENCH / 'before.txt', BENCH / 'after.txt')
    b, a = read(before), read(after)
    print('| Место | Показатель | База (без обработчиков) | До | После |')
    print('| --- | --- | ---: | ---: | ---: |')
    for phase, title in PHASES.items():
        base = b[(phase, 'base')] + a[(phase, 'base')]
        for key, name in METRICS:
            fmt = '{:.0f}' if key in ('fps', 'player_server_us', 'player_client_us', 'syncs_per_s') else '{:.1f}' if key != 'mspt' else '{:.2f}'
            cells = [fmt.format(mean(rows, key)) for rows in (base, b[(phase, 'mod')], a[(phase, 'mod')])]
            print(f'| {title} | {name} | ' + ' | '.join(cells) + ' |')
    print(f'\nОкон: до {sum(len(v) for k, v in b.items() if k[1] == "mod")}, после {sum(len(v) for k, v in a.items() if k[1] == "mod")}.')


if __name__ == '__main__':
    main()
