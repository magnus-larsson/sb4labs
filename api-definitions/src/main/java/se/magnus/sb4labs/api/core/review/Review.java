package se.magnus.sb4labs.api.core.review;

public record Review(
  int productId,
  int reviewId,
  String author,
  String subject,
  String content) {
}
