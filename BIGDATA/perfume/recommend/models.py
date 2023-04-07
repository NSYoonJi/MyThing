from django.db import models

# Create your models here.
class Perfume(models.Model):
    perfume_id = models.AutoField(primary_key = True)
    name = models.CharField(max_length = 100)
    brand = models.CharField(max_length = 100)
    image_url = models.TextField()
    notes = models.CharField(max_length = 255)
    info = models.TextField()

    class Meta:
        db_table = 'perfume'

    def __str__(self):
        return self.name

class Review(models.Model):
    review_id = models.AutoField(primary_key = True)
    username = models.CharField(max_length = 100)
    perfume_id = models.IntegerField()
    rating = models.IntegerField()

    class Meta:
        db_table = 'review_db'

    def __str__(self):
        return self.username