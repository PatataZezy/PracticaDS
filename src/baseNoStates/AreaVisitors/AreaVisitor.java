package baseNoStates.AreaVisitors;

import baseNoStates.Partition;
import baseNoStates.Space;

public interface AreaVisitor {
  void visitPartition(Partition partition);

  void visitSpace(Space space);
}
