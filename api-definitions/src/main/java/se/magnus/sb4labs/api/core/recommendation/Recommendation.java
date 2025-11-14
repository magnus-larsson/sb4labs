package se.magnus.sb4labs.api.core.recommendation;

public record Recommendation(
  int productId,
  int recommendationId,
  String author,
  int rate,
  String content) {
}
