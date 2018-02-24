(ns etaoin.util)

(defn map-or-nil?
  [x]
  (or (map? x) (nil? x)))

(defn deep-merge
  [& vals]
  (if (every? map-or-nil? vals)
    (apply merge-with deep-merge vals)
    (if (every? sequential? vals)
      (apply concat vals)
      (last vals))))

(defmacro defmethods
  "Declares multimethods in batch. For each dispatch value from
  dispatch-vals, creates a new method."
  [multifn dispatch-vals & fn-tail]
  `(doseq [dispatch-val# ~dispatch-vals]
     (defmethod ~multifn dispatch-val# ~@fn-tail)))

(defn sec->ms [sec]
  (* sec 1000))

(defn ms->sec [ms]
  (/ ms 1000))

(defn dispatch-types
  [& args]
  (mapv class args))

(defn error
  ([msg]
   (throw (Exception. msg)))
  ([tpl & args]
   (error (apply format tpl args))))

(defn random-port
  "Returns a random port skiping the first 1024 ones."
  []
  (let [max-port 65536
        offset 1024]
    (-> max-port
        (- offset)
        (rand-int)
        (+ offset))))
