package com.mdorst.container.hash;

import com.mdorst.container.list.LinkedList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HashMap<Key, Value> {
    private class Pair {
        public Key key;
        public Value value;
        public Pair(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private Function<Key, Integer> hash;
    private List<LinkedList<Pair>> table;

    public void add(Key key, Value value) {
        table.get(hash.apply(key) % 20).add(new Pair(key, value));
    }

    public Value get(Key key) {
        int bucket = hash.apply(key) % 20;
        for (int slot = 0; slot < table.get(bucket).size(); slot++) {
            Pair pair = table.get(bucket).get(slot);
            if (key.equals(pair.key)) {
                return pair.value;
            }
        }
        return null;
    }

    public void reset() {
        table = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            table.add(new LinkedList<>());
        }
    }

    public HashMap(Function<Key, Integer> hash) {
        this.hash = hash;
        reset();
    }
}
