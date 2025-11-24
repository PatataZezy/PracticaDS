package baseNoStates.AreaVisitors;

import baseNoStates.Partition;
import baseNoStates.Space;

// Visitor for DirectoryAreas where it traverses the entire tree to retrieve a specific set of data
public interface AreaVisitor {
  void visitPartition(Partition partition);

  void visitSpace(Space space);
}
