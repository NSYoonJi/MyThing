from django.urls import path
from . import views


urlpatterns = [
    path('survey/<int:perfume_id>/', views.survey),
    path('survey-reverse/<int:perfume_id>/', views.survey_reverse),
    path('recommend-review/<int:perfume_id>/', views.recommend_review),
    path('prefer/', views.prefer),
]
