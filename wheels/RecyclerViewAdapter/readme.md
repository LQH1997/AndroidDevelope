An adapter for RecyclerView
In the main class, use
```
 private RecyclerView mRecyclerView;
 private LinearLayoutManager mLayoutManager;
 private MyAdapter mAdapter;

 mLayoutManager = new LinearLayoutManager(this);
 mRecyclerView.setLayoutManager(mLayoutManager);
 mRecyclerView.setHasFixedSize(true);
 mAdapter = new MyAdapter(myData);
 mRecyclerView.setAdapter(mAdapter);
```
to set the recyclerview.
