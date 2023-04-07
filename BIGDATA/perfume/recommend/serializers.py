from rest_framework import serializers
from .models import Perfume


class PerfumeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Perfume
        # fields = '__all__'
        fields = ('perfume',)
