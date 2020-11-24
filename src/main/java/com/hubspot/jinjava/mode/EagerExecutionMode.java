package com.hubspot.jinjava.mode;

import com.hubspot.jinjava.interpret.Context;
import com.hubspot.jinjava.lib.expression.EagerExpressionStrategy;
import com.hubspot.jinjava.lib.tag.eager.EagerTagDecorator;
import com.hubspot.jinjava.lib.tag.eager.EagerTagFactory;
import java.util.Optional;

public class EagerExecutionMode implements ExecutionMode {

  @Override
  public boolean isPreserveRawTags() {
    return true;
  }

  @Override
  public void prepareContext(Context context) {
    context
      .getAllTags()
      .stream()
      .filter(tag -> !(tag instanceof EagerTagDecorator))
      .map(tag -> EagerTagFactory.getEagerTagDecorator(tag.getClass()))
      .filter(Optional::isPresent)
      .forEach(maybeEagerTag -> context.registerTag(maybeEagerTag.get()));
    context.setExpressionStrategy(new EagerExpressionStrategy());
  }
}
