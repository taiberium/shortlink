# Shortlink


![Build and Test](https://github.com/taiberium/shortlink/workflows/Shortlink-CI/badge.svg)

For URL shorten algorithm used: [Bijective Function](https://en.wikipedia.org/wiki/Bijection) 
Transformation based on Base10 to Base63 convertation  

**Used for storage hazelcast by those reasons**:
- Use shards and able to unlimited scaling
- Hazelcast creates the backups of partitions and distributes them among members for redundancy.
- Each Hazelcast partition can have multiple replicas, which are distributed among the cluster members.
- Ability to add shards during runtime without application stop and with data auto rebalancing
- Able to persist data to any DB without performance degradation
- Have unique in-cluster ID generator based on timestamp, sequence and nodeId. Not brain
split, nor network failure can break such id generation.

To start app use:
````
docker-compose up -d 
of
docker-compose up -d --scale shortlink-server=5
(Hazelcast cluster memory will be accessible from all app replicas)
````
 
